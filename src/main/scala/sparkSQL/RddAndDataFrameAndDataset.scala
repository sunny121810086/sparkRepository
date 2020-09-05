package sparkSQL

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object RddAndDataFrameAndDataset {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext
    val lineRdd: RDD[String] = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\booksInfo")

    val arrayRdd: RDD[Array[String]] = lineRdd.map(line => line.split(" "))

    import spark.implicits._
    //转换方式一：RDD[] --> Dataset[]
    val caseClassRdd: RDD[BookAndAuthor] = arrayRdd.map(arr => BookAndAuthor(arr(0),arr(1)))

    println(caseClassRdd.collect().toBuffer)
    //导入隐式区域，使用toDS()将RDD[BookAndAuthor]转换成Dataset[BookAndAuthor]类型 //导入隐式区域。这里的spark是SparkSession对象的名字。
    val caseClassDataset: Dataset[BookAndAuthor] = caseClassRdd.toDS()
    //caseClassDataset.show()

    //转换方式二：RDD[] --> DataFram
    val tupleRdd: RDD[(String, String)] = arrayRdd.map(arr => (arr(0),arr(1))) //生成元组类型rdd
    val dataFrame1: DataFrame = tupleRdd.toDF("bookName","author") //添加结构信息,即字段名
    //或者
    val dataFrame2: DataFrame = caseClassRdd.toDF()  //RDD[BookAndAuthor]中已包含数据类型和结构信息,可以直接转

    //转换方式三：DataFrame --> Dataset[]
    //创建一个样例类BookAndAuthor2
    val dataset: Dataset[BookAndAuthor2] = dataFrame1.as[BookAndAuthor2]
    dataset.show()


    //Dataset[] --> DataFrame --> RDD[Row]
    val toDF1: DataFrame = dataset.toDF()
    val rddRow: RDD[Row] = dataFrame1.rdd //每行数据被封装成Row类型
    rddRow.foreach(row => {
      println(row.getString(0) + "===" + row.getString(1)) //对Row类型取值
    })
    //或者 Dataset[] --> RDD[]
    val toRdd: RDD[BookAndAuthor2] = dataset.rdd

    spark.stop() //释放资源
  }

}

case class BookAndAuthor(bookName: String, author: String)
case class BookAndAuthor2(bookName: String, author: String)
