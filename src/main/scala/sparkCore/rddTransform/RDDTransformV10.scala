package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV10 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4,5), 2)
    val rdd02 = sc.makeRDD(List(4,5,6,7,8), 2)

    //交集
    val rdd03A = rdd01.intersection(rdd02)
    println(rdd03A.collect.mkString(",")) //4,5
    //并集--不去重
    val rdd03B = rdd01.union(rdd02)
    println(rdd03B.collect.mkString(",")) //1,2,3,4,5,4,5,6,7,8

    //差集
    val rdd03C = rdd01.subtract(rdd02)
    println(rdd03C.collect.mkString(",")) //2,1,3

    //拉链--分区内元素个数相同
    //org.apache.spark.SparkException: Can only zip RDDs with same number of elements in each partition
    val rdd03D = rdd01.zip(rdd02)
    println(rdd03D.collect.mkString(",")) //(1,4),(2,5),(3,6),(4,7),(5,8)

  }
}