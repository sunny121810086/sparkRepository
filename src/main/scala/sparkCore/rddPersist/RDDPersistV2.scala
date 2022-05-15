package sparkCore.rddPersist

import org.apache.spark.sql.SparkSession

object RDDPersistV2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("hello java", "hello scala", "hello spark"),2)

    val rdd02 = rdd01.flatMap(x => {
      println("flatMap..."); x.split(" ")
    })
    val rdd03 = rdd02.map(x => {
      println("map..."); (x, 1)
    })

    //rdd03.cache() //persist(StorageLevel.MEMORY_ONLY)
    //rdd03.persist(StorageLevel.DISK_ONLY)
    //设置检查点存储路径，当作业即所有job完成后，不会被删除，可以跨作业使用
    //但是这里会重新执行一遍逻辑，所有这里flatMap...和map...仍然会被输出
    //所有为了提高效率，一般结合cache使用
    sc.setCheckpointDir("checkPoint")
    rdd03.cache()
    rdd03.checkpoint()

    val rdd04 = rdd03.reduceByKey(_ + _)
    rdd04.collect.foreach(println(_))

    println("*****************************")

    val rdd05 = rdd03.groupByKey()
    rdd05.collect.foreach(println(_))

    sc.stop()
  }
}
