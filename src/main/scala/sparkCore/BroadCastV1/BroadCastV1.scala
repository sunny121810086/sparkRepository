package sparkCore.BroadCastV1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object BroadCastV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(('A', 1), ('B', 2), ('C', 3)), 2)
    val rdd02 = sc.makeRDD(List(('A', 10), ('B', 20), ('C', 30), ('C', 40), ('D', 40)), 3)

    //普通的join会产生shuffle，性能较低
    val rdd03 = rdd01.join(rdd02)

    val map1: Map[Char, Int] = rdd01.collect.toMap

    /**
      * 分布式共享只读变量--广播变量Broadcast
      * map1为map算子中匿名函数的一个外部变量，闭包数据是以Task为单位发送的
      * 这样会造成同一个Executor中有多少个Task，就会有多少份map1的数据，占用大量内存
      * 而广播变量可以将闭包的数据保存到Executor内存中，只保存1份数据
      * 每个Executor中的所有Task会共享这1份数据
      **/
      //同过map实现join效果，不过由于闭包数据会以Task为单位发送，性能较差
    val rdd04: RDD[(Char, (Int, Int))] = rdd02.map {
      case (word, cnt) => {
        val cnt2 = map1.getOrElse(word, 0)
        (word, (cnt, cnt2))
      }
    }.filter(_._2._2 != 0)

    //分布式共享只读变量--广播变量
    val broad = sc.broadcast(map1)

    val rdd05: RDD[(Char, (Int, Int))] = rdd02.map {
      case (word, cnt) => {
        val cnt2 = broad.value.getOrElse(word, 0)
        (word, (cnt, cnt2))
      }
    }.filter(_._2._2 != 0)

    rdd05.collect().foreach(println(_))

    sc.stop()
  }
}
