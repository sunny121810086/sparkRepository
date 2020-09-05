package sparkSQL

import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DataFrameExamples {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val dataFrame1: DataFrame = spark.read.format("text").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\booksInfo")
    //    val dataFrame2: DataFrame = dataFrame1.map(row => {
    //      val bookname = row.getString(0).split(" ")(0)
    //      val author = row.getString(0).split(" ")(1)
    //      (bookname, author)
    //    }).toDF("bookName", "author")

    import spark.implicits._
    import org.apache.spark.sql.functions._
    val dataFrame2 = dataFrame1.map {
      case Row(line: String) => {
        val bookname = line.split(" ")(0)
        val author = line.split(" ")(1)
        (bookname, author)
      }
    }.toDF("bookName", "author")

    //查看DataFrame的Schema信息
    dataFrame2.printSchema()

    //需要导入 import org.apache.spark.sql.functions._
    dataFrame2.groupBy("author").agg(count("bookName")).show()

    //返回符合条件的所有列
    val dataFrame3 = dataFrame2.where("author='古龙'")

    val dataFrame4 = dataFrame1.toDF("booksInfo")
    dataFrame4.withColumn("bookname",explode(split(col("booksInfo")," "))).show()
    dataFrame4.select(explode(split(col("booksInfo")," ")).as("booksInfo2")).show()


    spark.stop()
  }
}