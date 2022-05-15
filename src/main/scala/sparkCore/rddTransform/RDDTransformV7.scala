package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV7 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,1,2,2,3,3))

    /**
      * def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
      *   map(x => (x, null)).reduceByKey((x, y) => x, numPartitions).map(_._1)
      * }
      * 去重说明：
      * 1.map将每条数据转换成(1,null),(1,null),(2,null)...
      * 2.reduceByKey算子对相同key的数据处理：(null,null) => null
      * 3.reduceByKey处理完后，传入父RDD(即rdd01)的分区数。这里返回RDD[(Int,Null)]类型
      * 4.map算子转换，取出key
      *
     **/

    val rdd01A = rdd01.distinct()

    println(rdd01A.collect.mkString(","))

    sc.stop()
  }
}
