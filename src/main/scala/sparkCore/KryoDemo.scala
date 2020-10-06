package sparkCore

import org.apache.spark.rdd.RDD
import org.apache.spark.serializer.JavaSerializer
import org.apache.spark.sql.SparkSession

object KryoDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .config("spark.serializer",classOf[JavaSerializer].getName) //注册Kryo序列化器，可以不写，源码已默认设置
      .config("spark.kryo.classesToRegister","Fiction") //指定需要序列化的类，多个逗号分隔
      .getOrCreate()

    val sc = spark.sparkContext

    val lineRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\booksInfo")

    val fictionRDD: RDD[Fiction] = lineRDD.map {
      line =>
        val arr = line.split(" ")
        val fiction = new Fiction(arr(0), arr(1))
        fiction
    }

    println(fictionRDD.collect.toBuffer)

  }
}

class Fiction(val name: String, val author: String) extends Serializable {
  override def toString: String = s"书名：$name|作者：$author"
}
