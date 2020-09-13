package sparkSQL

import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object BayesParam {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext

    val lineRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\bayesparam\\themeInfo")

    import spark.implicits._

    val themeInfoDF = lineRDD.map {
      line => {
        val arr = line.split(" ")
        val groupId = arr(0)
        val themeId = arr(1)
        val theme_ver = arr(2)
        val price_level = arr(3)
        val exposure_users = arr(4)
        val click_users = arr(5)
        val payment_users = arr(6)
        val payment_amt = arr(7)
        (groupId, themeId, theme_ver, price_level, exposure_users, click_users, payment_users, payment_amt)
      }
    }.toDF("groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "payment_amt")

    import org.apache.spark.sql.functions._

    val key = "key"
    val values = Array("ctr", "cvr", "ctcvr", "arpu")

    val themeInfoCtrDF = themeInfoDF.withColumn(key, concat_ws("-", col("groupId"), col("theme_ver")))
      .withColumn("ctr", col("click_users") / col("exposure_users"))
      .withColumn("cvr", col("payment_users") / col("click_users"))
      .withColumn("ctcvr", col("payment_users") / col("exposure_users"))
      .withColumn("arpu", col("payment_amt") / col("exposure_users"))
      .selectExpr(key, "groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "payment_amt","ctr", "cvr", "ctcvr", "arpu")

    //获取分组key的ctr、cvr、ctcvr、arpu指标的alpha、beta参数
    var tmp_themeInfoCtrDF = themeInfoCtrDF.select(key).distinct()

    for (value <- values) {
      //计算平均数mean和方差variance
      val meanAndVarianceMap: Map[String, Array[Double]] = evaluateMeanAndVariance(key, value, themeInfoCtrDF)
      val alphaAndBetaMap: Map[String, Array[Double]] = evaluateAlphaAndBeta(meanAndVarianceMap)

      //通过实名函数定义UDF
      val getMeanUDF = udf((key: String) => meanAndVarianceMap.getOrElse(key, Array(0.0, 0.0))(0))
      val getVarianceUDF = udf((key: String) => meanAndVarianceMap.getOrElse(key, Array(0.0, 0.0))(1))
      val getAlphaUDF = udf((key: String) => alphaAndBetaMap.getOrElse(key, Array(0.0, 0.0))(0))
      val getBetaUDF = udf((key: String) => alphaAndBetaMap.getOrElse(key, Array(0.0, 0.0))(1))

      if (value.equals("ctr")) {
        tmp_themeInfoCtrDF = tmp_themeInfoCtrDF
          .withColumn(value+"_mean", getMeanUDF(col(key)))
          .withColumn(value+"_var",getVarianceUDF(col(key)))
          .withColumn(value+"_a", getAlphaUDF(col(key)))
          .withColumn(value+"_b",getBetaUDF(col(key)))
      } else {
        tmp_themeInfoCtrDF = tmp_themeInfoCtrDF
          .withColumn(value+"_a", getAlphaUDF(col(key)))
          .withColumn(value+"_b",getBetaUDF(col(key)))
      }
    }

    tmp_themeInfoCtrDF = tmp_themeInfoCtrDF.select(key,"ctr_mean","ctr_var","ctr_a","ctr_b","cvr_a","cvr_b","ctcvr_a","ctcvr_b","arpu_a","arpu_b")

    //关联themeInfoCtrDF，计算贝叶斯平滑修正后的ctr、cvr、ctcvr、arpu
    val resultDF = themeInfoCtrDF.join(tmp_themeInfoCtrDF,Seq(key))
      .select(key,"groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "payment_amt",
        "ctr", "cvr", "ctcvr", "arpu",
        "ctr_mean","ctr_var",
        "ctr_a","ctr_b","cvr_a","cvr_b","ctcvr_a","ctcvr_b","arpu_a","arpu_b")
      .withColumn("b_ctr",(col("click_users")+col("ctr_a")) / (col("exposure_users")+col("ctr_a")+col("ctr_b")))
      .withColumn("b_cvr",(col("payment_users")+col("cvr_a")) / (col("click_users")+col("cvr_a")+col("cvr_b")))
      .withColumn("b_ctcvr",(col("payment_users")+col("ctcvr_a")) / (col("exposure_users")+col("ctcvr_a")+col("ctcvr_b")))
      .withColumn("b_arpu",(col("payment_amt")+col("ctcvr_a")) / (col("exposure_users")+col("ctcvr_a")+col("ctcvr_b")))
      .select(key,"groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "payment_amt",
        "ctr", "cvr", "ctcvr", "arpu",
        "ctr_mean","ctr_var",
        "ctr_a","ctr_b",
        "b_ctr","b_cvr","b_ctcvr","b_arpu")

    resultDF.show()
  }


  def evaluateAlphaAndBeta(meanAndVarianceMap: Map[String, Array[Double]]): Map[String, Array[Double]] = {
    meanAndVarianceMap.map {
      case (key: String, meanAndVariance: Array[Double]) => {
        val mean = meanAndVariance(0)
        val variance = meanAndVariance(1)
        var tmp = 0.0
        if (mean != 0) {
          tmp = mean * (1 - mean) / variance - 1
        }
        val alpha = mean * tmp
        val beta = (1 - mean) * tmp
        (key, Array(alpha, beta))
      }
    }
  }

  def evaluateMeanAndVariance(key: String, value: String, themeInfoCtrDF: DataFrame): Map[String, Array[Double]] = {
    import org.apache.spark.sql.functions._
    val tmpDF = themeInfoCtrDF.withColumn(key, concat_ws("-", col("groupId"), col("theme_ver")))
      .selectExpr(key, "groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", value)
      .groupBy(key)
      .agg(avg(value) as "mean", variance(value) as "variance")
      .selectExpr(key, "mean", "variance")
    val rdd = tmpDF.rdd


    tmpDF.rdd.map {
      case Row(key: String, mean: Double, variance: Double) => {
        (key, Array(mean, variance))
      }
    }.collect().toMap
  }
}
