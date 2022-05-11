package sparkCore.rddTransform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


object RDDTransformV4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01: RDD[List[Int]] = sc.makeRDD[List[Int]](List(List(1,2), List(3,4), List(5,6)),2)
    val rdd02: RDD[String]= sc.makeRDD[String](List("hello scala", "hello spark","hello java"),2)
    val rdd03: RDD[Any] = sc.makeRDD(List(List(1,2), 3.14, "hello spark"),2)

    /**
     *  def flatMap[U: ClassTag](f: T => TraversableOnce[U]): RDD[U] = withScope {
      *   val cleanF = sc.clean(f)
      *   new MapPartitionsRDD[U, T](this, (context, pid, iter) => iter.flatMap(cleanF))
      * }
     **/

    //传入的函数需要返回一个可迭代的集合
    val rdd01A: RDD[Int] = rdd01.flatMap(list => list.filter(_ % 2 == 0))
    val rdd02A: RDD[String] = rdd02.flatMap(word => word.split(" "))
    //使用模式匹配去处理不同类型数据，也可以传入一个偏函数去处理
    val rdd03A = rdd03.flatMap {
      elem => {
        elem match {
          case list: List[Int] => list
          case num: Double => List(num)
          case word: String => word.split(" ")
          case _ => Nil
        }
      }
    }
    //模式匹配可转换成偏函数
    val rdd03B = rdd03.flatMap(partialFunc)

    println(rdd03B.collect.toBuffer)

    sc.stop()
  }

  def partialFunc: PartialFunction[Any,List[Any]] = {
    case list: List[Int] => list
    case num: Double => List(num)
    case word: String => word.split(" ").toList
    case _ => Nil
  }
}
