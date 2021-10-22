package sparkSQL

import org.apache.spark.sql.SparkSession

object GroupByKey {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext

    val lineRDD1 = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\groupbyKey")
    val lineRDD2 = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\groupbyKeyV2")

    import spark.implicits._
    //召回和推荐
    val recallAndRecommDF = lineRDD1.map(line => {
      val arr = line.split(" ")
      val device_id = arr(0)
      val recall_id = arr(1)
      val recomm_id = arr(2)
      val exposure_cnt = arr(3)
      val click_cnt = arr(4)
      (device_id, recall_id, recomm_id, exposure_cnt, click_cnt)
    }).toDF("device_id", "recall_id", "recomm_id", "exposure_cnt", "click_cnt")

    //召回
    val recallDF = lineRDD2.map(line => {
      val arr = line.split(" ")
      val device_id = arr(0)
      val recall_id = arr(1)
      val exposure_cnt = arr(2)
      val click_cnt = arr(3)
      (device_id, recall_id, exposure_cnt, click_cnt)
    }).toDF("device_id", "recall_id", "exposure_cnt", "click_cnt")

    recallAndRecommDF.createTempView("recall_recomm_tbale")
    recallDF.createTempView("recall_table")

    val SQL01 =
      """
        |select * from recall_recomm_tbale
      """.stripMargin

    val SQL02 =
      """
        |select * from recall_table
      """.stripMargin

    spark.sql(SQL01).show()
    spark.sql(SQL02).show()
  }

}
