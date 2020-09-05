package sparkSQL

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object GroupAndTopNSql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext

    val value = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\advertisementInfo")
    val lineRDD = value
    //  val dataFrame1: DataFrame = spark.read.format("textfile").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\advertisementInfo")

    val splitRDD: RDD[Array[String]] = lineRDD.map(_.split(" "))

    import spark.implicits._
    val caseclassRDD: RDD[Advertisement] = splitRDD.map(arr => Advertisement(arr(0).toLong, arr(1), arr(2), arr(3), arr(4)))
    val dataFrame1 = caseclassRDD.toDF()

    dataFrame1.createTempView("ads_advertisementInfo")

    val topNSQL =
      """
        |select
        |provence,
        |ad,
        |count
        |from
        |(
        |select
        |provence,
        |ad,
        |count,
        |row_number() over(partition by provence order by count desc) top
        |from
        |(
        |select provence, ad,count(*) count
        |from ads_advertisementInfo
        |group by provence,ad
        |)a
        |)b
        |where b.top=1
      """.stripMargin

    val dataFrame2: DataFrame = spark.sql(topNSQL)

   dataFrame2.write.format("parquet").mode("overwrite").save("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\out\\advertisementInfo")

    spark.stop()


  }

}

case class Advertisement(timestample: Long, provence: String, city: String, user: String, ad: String)

