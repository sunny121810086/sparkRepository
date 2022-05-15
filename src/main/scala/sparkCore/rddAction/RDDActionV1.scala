package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDActionV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4,5), 2)

    //行动算子--触发整个job的执行
    //按照分区顺序将不同分区的数据采集到Driver端的内存中
    val collect: Array[Int] = rdd01.collect

    val reduce: Int = rdd01.reduce(_+_)

    //数据源中数据的个数
    val count: Long = rdd01.count()

    //获取数据源中第一个数据
    val first: Int = rdd01.first()

    //取数据源中的前多少条
    val topN: Array[Int] = rdd01.take(3)

    //aggregate--初始值会先参与分区内的聚合操作，然后分区间的聚合操作也会再次参与
    //aggregateByKey----初始值只会参与分区间的聚合操作
    val aggregate = rdd01.aggregate(10)(_+_,_+_) //输出45

    //表示分区间和分区内聚合操作逻辑相同
    val fold = rdd01.fold(10)(_+_) //输出45

    val rdd02 = sc.makeRDD(List(1,2,2,3,3,3), 2)
    //统计数据源中各个元素的个数
    val map: collection.Map[Int, Long] = rdd02.countByValue()
    println(map) //Map(2 -> 2, 1 -> 1, 3 -> 3)

    val rdd03 = sc.makeRDD(List(("A", 1), ("A", 2), ("B", 3), ("C", 4),("C",4)),4)
    //统计数据源中key的个数
    val map2: collection.Map[String, Long] = rdd03.countByKey()
    val map3 = rdd03.countByValue()
    println(map2) //Map(A -> 2, B -> 1, C -> 2)
    println(map3) //Map((A,1) -> 1, (B,3) -> 1, (C,4) -> 2, (A,2) -> 1)

    //foreach返回值为()
    //RDD中的方法是将计算逻辑发送到Executor端(分布式节点)执行
    //Scala集合对象的方法是在同一个节点的内存中完成的
    //为了区分不同的处理效果，将RDD的方法称为算子
    rdd03.foreach(println(_))

    //在项目路径下创建一个output目录，将RDD内的元素按照textfile格式存储进去
    //rdd03.repartition(2).saveAsTextFile("output")
    sc.stop()
  }
}