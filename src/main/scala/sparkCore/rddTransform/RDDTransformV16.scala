package sparkCore.rddTransform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDTransformV16 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext


    val rdd01 = sc.makeRDD(List(("AA", 1), ("BB", 2), ("CC", 3), ("DD", 4)), 2)
    val rdd02 = sc.makeRDD(List(("AA", 1.1), ("BB", 2.2), ("CC", 3.3), ("CC", 4.4)), 2)

    val rdd01A: RDD[(String, (Iterable[Int], Iterable[Double]))] = rdd01.cogroup(rdd02)
    rdd01A.collect.foreach(println(_))
    // (DD,(CompactBuffer(4),CompactBuffer()))
    // (BB,(CompactBuffer(2),CompactBuffer(2.2)))
    // (CC,(CompactBuffer(3),CompactBuffer(3.3, 4.4)))
    // (AA,(CompactBuffer(1),CompactBuffer(1.1)))

    sc.stop()
  }
}