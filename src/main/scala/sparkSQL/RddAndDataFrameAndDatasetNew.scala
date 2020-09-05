package sparkSQL

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object RddAndDataFrameAndDatasetNew {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate() //导入隐式区域
    //val df: DataFrame = spark.read.json("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\personInfo")
    val df = spark.read.format("json").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\personInfo")
    df.createTempView("famouStar")
    val query1 = spark.sql("select * from famouStar where gender='女'")
    query1.show()
    query1.createTempView("tmp_famouStar_table1")
    val query2 = spark.sql("select * from tmp_famouStar_table1 where age < 30")
    //4种存储方式：Overwrite--覆盖；Append--追加，生成新的文件；ErrorIfExists--默认方式，文件存在报错；Ignore--数据存在则忽略
    query2.write.format("json").mode("Overwrite").save("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\out")
    query2.show()
    //替换指定字段的值，实现数据清洗。这里有个问题，age字段转换失败？
    val query3 = query2.na.replace(Array("age","gender","name"),Map(("李一桐","520李一桐"),("女","femal"),(28,18)))
    query3.show()

    val date = getCurrentDate
    val tmp_SQL =
      s"""
        |select
        |name,
        |age,
        |gender,
        |from_unixtime(unix_timestamp('$date','yyyyMMdd'),'yyyy-MM-dd') nowdate
        |from tmp_famouStar_table1
      """.stripMargin
    spark.sql(tmp_SQL).show()

    import spark.implicits._

    //getDataFrame(spark,tmp_SQL).show()
    //DataFrame -> Dataset[]
    val dataset1: Dataset[FamousStar] = query2.as[FamousStar]

    //DataFrame -> RDD[]
    val toRdd1: RDD[Row] = query2.rdd

    //Dataset[] -> RDD[]
    val toRdd2: RDD[FamousStar] = dataset1.rdd

    spark.stop()
  }
  def getDataFrame(spark: SparkSession,sql: String): DataFrame = {
    val sqlContext = spark.sqlContext
    val dataFrame: DataFrame = sqlContext.sql(sql)
    dataFrame
  }

  def getCurrentDate = {
    val date = new Date()
    val format = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
    val formatDate: String = format.format(date)
    formatDate
  }
}
case class FamousStar(name: String, age: BigInt, gender: String)
