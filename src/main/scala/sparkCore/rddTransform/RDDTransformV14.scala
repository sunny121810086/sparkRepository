package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV14 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    /**
      * PairRDDFunctions类中的方法：
      * def combineByKey[C](
      * createCombiner: V => C,
      * mergeValue: (C, V) => C,
      * mergeCombiners: (C, C) => C): RDD[(K, C)] = self.withScope {
      * combineByKeyWithClassTag(createCombiner, mergeValue, mergeCombiners)(null)
      * }
      * 说明：combineByKey适合分区内和分区间聚合逻辑不同时使用
      * createCombiner: V => C 对分区内相同key的第一个value进行类型转换
      * mergeValue: (C, V) => C  对同一个分区内相同key的value进行聚合操作
      * mergeCombiners: (C, C) => C 分区间相同key的value进行聚合操作
      **/
    val rdd01 = sc.makeRDD(List(("AA", 2), ("AA", 4), ("AA", 8), ("BB", 3), ("BB", 9), ("BB", 27)), 3)

    val rdd01A = rdd01.combineByKey(
      v1 => (v1, 1),
      (tup: (Int, Int), v: Int) => (tup._1 + v, tup._2 + 1),
      (tup1: (Int, Int), tup2: (Int, Int)) => (tup1._1 + tup2._1, tup1._2 + tup2._2)
    )
    val rdd01B = rdd01A.map(tup => (tup._1, tup._2._1 / tup._2._2.toDouble))
    println(rdd01A.collect.toBuffer) //ArrayBuffer((BB,(39,3)), (AA,(14,3)))
    println(rdd01B.collect.toBuffer) //ArrayBuffer((BB,13.0), (AA,4.666666666666667))

    sc.stop()
  }
}