package sparkCore.rddPartitioner

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner}
import org.apache.spark.sql.SparkSession

object RDDPartitionerV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("a0001","b0001","b0002","c0001","c0002","c0003"),3)

    val rdd02: RDD[(String, Int)] = rdd01.map((_,1))

    val rdd03 = rdd02.partitionBy(new MyPartitioner(2))

    rdd03.saveAsTextFile("output")
  }
}

class MyPartitioner(partitions: Int) extends Partitioner {
  override def numPartitions: Int = partitions

  override def getPartition(key: Any): Int = {
    val ch = key.toString.substring(0,1)
    ch.hashCode % numPartitions
  }
}
