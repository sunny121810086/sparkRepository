package sparkCore.rddPersist

import org.apache.spark.sql.SparkSession

object RDDPersistV3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("hello java", "hello scala", "hello spark"),2)

    val rdd02 = rdd01.flatMap(_.split(" "))
    val rdd03 = rdd02.map((_,1))

    //rdd03.cache()
    sc.setCheckpointDir("checkPoint")
    rdd03.checkpoint()

    println(rdd03.toDebugString) //执行Action类型算子之前的血缘关系

    val rdd04 = rdd03.reduceByKey(_ + _)
    rdd04.collect

    println("*********************************")
    println(rdd03.toDebugString)
    sc.stop()

    /**
     * 总结：
     * cache、persist会在血缘关系中添加新的依赖，当数据丢失时，可以重新获取数据。
      * (2) MapPartitionsRDD[2] at map at RDDPersistV3.scala:17 [Memory Deserialized 1x Replicated]
      * |  MapPartitionsRDD[1] at flatMap at RDDPersistV3.scala:16 [Memory Deserialized 1x Replicated]
      * |  ParallelCollectionRDD[0] at makeRDD at RDDPersistV3.scala:14 [Memory Deserialized 1x Replicated]
      * *********************************
      * (2) MapPartitionsRDD[2] at map at RDDPersistV3.scala:17 [Memory Deserialized 1x Replicated]
      * |       CachedPartitions: 2; MemorySize: 560.0 B; ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
      * |  MapPartitionsRDD[1] at flatMap at RDDPersistV3.scala:16 [Memory Deserialized 1x Replicated]
      * |  ParallelCollectionRDD[0] at makeRDD at RDDPersistV3.scala:14 [Memory Deserialized 1x Replicated]
      *
      * checkpointh会切断血缘关系，建立新的血缘关系，相当于改变了数据源。如下：
      * (2) MapPartitionsRDD[2] at map at RDDPersistV3.scala:17 []
      * |  MapPartitionsRDD[1] at flatMap at RDDPersistV3.scala:16 []
      * |  ParallelCollectionRDD[0] at makeRDD at RDDPersistV3.scala:14 []
      * *********************************
      * (2) MapPartitionsRDD[2] at map at RDDPersistV3.scala:17 []
      * |  ReliableCheckpointRDD[4] at collect at RDDPersistV3.scala:26 []
      *
     *
     **/
  }
}
