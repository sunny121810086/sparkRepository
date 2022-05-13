package sparkCore.rddTransform

import org.apache.spark.HashPartitioner
import org.apache.spark.sql.SparkSession

object RDDTransformV11 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    /**
     *  RDD伴生类中的隐式函数：
      * implicit def rddToPairRDDFunctions[K, V](rdd: RDD[(K, V)])
      *   (implicit kt: ClassTag[K], vt: ClassTag[V], ord: Ordering[K] = null): PairRDDFunctions[K, V] = {
      *   new PairRDDFunctions(rdd)
      * }
      *
      * PairRDDFunctions类中的partitionBy方法：
      * def partitionBy(partitioner: Partitioner): RDD[(K, V)] = self.withScope {
      *   if (keyClass.isArray && partitioner.isInstanceOf[HashPartitioner]) {
      *     throw new SparkException("HashPartitioner cannot partition array keys.")
      * }
      * if (self.partitioner == Some(partitioner)) {
      *   self
      * } else {
      *   new ShuffledRDD[K, V, V](self, partitioner)
      * }
      * }
      *
      * HashPartitioner类中分区方法：key.hashCode % numPartitions
      * def getPartition(key: Any): Int = key match {
      *   case null => 0
      *   case _ => Utils.nonNegativeMod(key.hashCode, numPartitions)
      * }
      *
      *说明：
      * 这里是通过隐式转换--implicit conversion来访问PairRDDFunctions实例中的方法
      *
     **/
    val rdd01 = sc.makeRDD(List(("AA",1),("AA",2),("BB",3),("CC",4)), 3)
    val rdd01A = rdd01.partitionBy(new HashPartitioner(rdd01.getNumPartitions))
    val rdd01B = rdd01A.mapPartitionsWithIndex {
      (index, iter) => {
        List((index, iter.toList)).iterator
      }
    }
    println(rdd01B.collect.toBuffer) //ArrayBuffer((0,List((BB,3))), (1,List((AA,1), (AA,2))), (2,List((CC,4))))

  }
}
