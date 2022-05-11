package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV9 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("A:1", "B:3", "C:5", "D:4", "E:2", "F:0"), 3)

    /**
      * def sortBy[K](
      *     f: (T) => K,
      *     ascending: Boolean = true,
      *     numPartitions: Int = this.partitions.length)
      *     (implicit ord: Ordering[K], ctag: ClassTag[K]): RDD[T] = withScope {
      *   this.keyBy[K](f)
      *       .sortByKey(ascending, numPartitions)
      *       .values
      * }
      *说明：
      * ascending--默认升序
      **/
    val rdd01A = rdd01.sortBy(str => str.split(":")(1), false)
    val rdd01B = rdd01.sortBy(_.split(":")(1),false)
    println(rdd01B.collect.mkString("\n"))
  }
}
