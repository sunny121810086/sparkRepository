package sparkCore

import org.apache.spark.rdd.RDD
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object KryoDemo {
  def main(args: Array[String]): Unit = {
    //        val spark = SparkSession
    //          .builder()
    //          .appName(this.getClass.getName)
    //          .master("local[2]")
    //          .config("spark.serializer",classOf[JavaSerializer].getName) //注册Kryo序列化器，可以不写，源码已默认设置
    //          .config("spark.kryo.classesToRegister","Fiction") //指定需要序列化的类，多个逗号分隔
    //          .getOrCreate()
    //
    //        val sc = spark.sparkContext

    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
      .set("spark.serializer", classOf[KryoSerializer].getName) ////注册Kryo序列化器，可以不写，源码已默认设置
      .registerKryoClasses(Array(classOf[Author])) ////指定需要序列化的类

    val sc = new SparkContext(conf)

    val lineRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\booksInfo")

    val author = new Author

    val fictionRDD = lineRDD.map {
      line =>
        val arr = line.split(" ")
        //闭包应用，Executor中每个task任务都会创建一个不同的实例，如果改成广播变量，则每个Executor中所有的task任务共享一个实例
        val info: mutable.Map[String, Int] = author.getAuthorInfo //闭包，引入外界变量需要序列化
        val age = info(arr(1))
        (arr(0),arr(1),age)
    }

    println(fictionRDD.collect.toBuffer)

  }
}

case class Fiction (name: String, author: String){

  override def toString: String = s"书名：$name|作者：$author"
}

class Author extends Serializable {
  import scala.collection.mutable
  private val authorInfo: mutable.Map[String,Int] = mutable.Map(("金庸",999),("古龙",888))
  def alterAuthorInfo(name: String, age: Int): Unit = {
    if (authorInfo.contains(name)) {
      authorInfo(name) = age
    } else {
      authorInfo += name -> age
    }
  }

  def getAuthorInfo: mutable.Map[String, Int] = authorInfo
}
