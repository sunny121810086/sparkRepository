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
        (groupId, themeId, theme_ver, price_level, exposure_users, click_users, payment_users)
      }
    }.toDF("groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users")

    import org.apache.spark.sql.functions._

    val themeInfoCtrDF = themeInfoDF.withColumn("ctr", col("click_users") / col("exposure_users"))
      .selectExpr("groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "ctr")


    //计算平均数mean和方差variance
    val key = "key"
    val meanAndVarianceMap: Map[String, Array[Double]] = evaluateMeanAndVariance(key, themeInfoCtrDF)
    val alphaAndBetaMap: Map[String, Array[Double]] = evaluateAlphaAndBeta(meanAndVarianceMap)

    //通过实名函数定义UDF
    val getMeanUDF = udf((key: String) => meanAndVarianceMap.getOrElse(key, Array(0.0, 0.0))(0))
    val getVarianceUDF = udf((key: String) => meanAndVarianceMap.getOrElse(key, Array(0.0, 0.0))(1))
    val getAlphaUDF = udf((key: String) => alphaAndBetaMap.getOrElse(key, Array(0.0, 0.0))(0))
    val getBetaUDF = udf((key: String) => alphaAndBetaMap.getOrElse(key, Array(0.0, 0.0))(1))

    val tmp_themeInfoCtrDF = themeInfoCtrDF.withColumn(key, concat_ws("-", col("groupId"), col("theme_ver")))

    tmp_themeInfoCtrDF.withColumn("mean", getMeanUDF(col(key)))
      .withColumn("variance", getVarianceUDF(col(key)))
      .withColumn("alpha", getAlphaUDF(col(key)))
      .withColumn("beta", getBetaUDF(col(key)))
      .withColumn("b_ctr", (col("click_users") + getAlphaUDF(col(key))) / (col("exposure_users") + getAlphaUDF(col(key)) + getBetaUDF(col(key))))
      .selectExpr(key, "groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "mean", "variance", "alpha", "beta", "ctr", "b_ctr")
      .show()

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

  def evaluateMeanAndVariance(key: String, themeInfoCtrDF: DataFrame): Map[String, Array[Double]] = {
    import org.apache.spark.sql.functions._
    val tmpDF = themeInfoCtrDF.withColumn(key, concat_ws("-", col("groupId"), col("theme_ver")))
      .selectExpr(key, "groupId", "themeId", "theme_ver", "price_level", "exposure_users", "click_users", "payment_users", "ctr")
      .groupBy(key)
      .agg(avg("ctr") as "mean", variance("ctr") as "variance")
      .selectExpr(key, "mean", "variance")
    val rdd = tmpDF.rdd


    tmpDF.rdd.map {
      case Row(key: String, mean: Double, variance: Double) => {
        (key, Array(mean, variance))
      }
    }.collect().toMap
  }
}
