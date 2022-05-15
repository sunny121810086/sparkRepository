package sparkCore.rddPersist

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

object RDDPersistV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("hello java", "hello scala", "hello spark"))

    val rdd02 = rdd01.flatMap(x => {
      println("flatMap..."); x.split(" ")
    })
    val rdd03 = rdd02.map(x => {
      println("map..."); (x, 1)
    })

    //rdd03.cache() //persist(StorageLevel.MEMORY_ONLY)
    //持久化到磁盘，这里是临时文件，作业即所有job完成后会删除
    rdd03.persist(StorageLevel.DISK_ONLY)

    val rdd04 = rdd03.reduceByKey(_ + _)
    rdd04.collect.foreach(println(_))

    println("*****************************")

    //没有进行持久化操作前，这里rdd03对象被重用，由于RDD是不存储数据的，数据无法重用，
    //只能再次重新执行一遍逻辑，获取数据，所有这里flatMap...和map...仍然会被输出
    val rdd05 = rdd03.groupByKey()
    rdd05.collect.foreach(println(_))

    sc.stop()

    /**
      * 总结：
      * cache()实际调用也是persist(StorageLevel.MEMORY_ONLY)，默认缓存在JVM堆内存中，当内存溢出时会丢弃一部分数据
      * persist(StorageLevel.DISK_ONLY) 可以设置不同的存储级别
      * 当执行步骤耗时较长或者数据比较重要，可以考虑持久化操作
      * cache、persist都是将数据进行临时存储，作业即所有job完成后会自动删除
      **/
  }
}
