package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV8 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4,5,6,7,8),3)

    val rdd01A = rdd01.coalesce(2)

    /**
      * def coalesce(numPartitions: Int, shuffle: Boolean = false,
      *              partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
      *              (implicit ord: Ordering[T] = null): RDD[T]
      *
      * 当需要扩大分区时，可直接调用repartition算子
      * def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
      *   coalesce(numPartitions, shuffle = true)
      * }
     **/

    val rdd01B = rdd01.coalesce(
      5,
      true
    )


    val rdd01C = rdd01.repartition(5)

    println(rdd01C.collect.mkString(","))

    sc.stop()

  }
}
