package sparkCore.rddTransform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDTransformV12 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    /**
      * PairRDDFunctions类中的方法：
      * reduceByKey有多个重载方法
      * def reduceByKey(func: (V, V) => V): RDD[(K, V)] = self.withScope {
      *   reduceByKey(defaultPartitioner(self), func)
      * }
      * 说明：
      * groupByKey分组过程中，会发生shuffle，数据需要溢写磁盘(shuffle write和shuffle read)
      * reduceByKey可以在shuffle前对相同分区的数据进行预聚合，减少溢写磁盘的数据量
      **/
    val rdd01 = sc.makeRDD(List(("AA",1),("AA",2),("BB",3),("CC",4)), 3)

    //相同key的数据进行value值的聚合操作
    val rdd01A = rdd01.reduceByKey((v1,v2) => v1 + v2)
    println(rdd01A.collect.toBuffer) //ArrayBuffer((BB,3), (AA,3), (CC,4))

    //相同key的数据进行value值的分组操作
    val rdd01B: RDD[(String, Iterable[Int])] = rdd01.groupByKey()
    //按照一定规则分组后，会将数据整体放到一个组中
    val rdd01C: RDD[(String, Iterable[(String, Int)])] = rdd01.groupBy(_._1)

  }
}