package sparkCore.accumulator

import org.apache.spark.sql.SparkSession
import org.apache.spark.util.AccumulatorV2
import scala.collection.mutable

object AccumulatorV4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List("hello", "java", "java", "scala", "scala", "scala"))

    //自定义累加器实现单词统计--可参考longAccumulator的实现过程
    val myAcc = new MyAccumulator
    sc.register(myAcc, "myAcc")

    rdd01.foreach {
      word => {
        myAcc.add(word)
      }
    }

    val wordcount = myAcc.value
    println(wordcount) //Map(scala -> 3, java -> 2, hello -> 1)

    sc.stop()
  }
}

/**
  * 自定义累加器 --实现wordcount
  * 通过累加器方式，没有shuffle阶段
  *
 **/

class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]] {
  private val map = mutable.Map[String, Long]()

  //判断是否为初始状态
  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = new MyAccumulator

  //重置累加器
  override def reset(): Unit = map.clear()

  override def add(word: String): Unit = {
    val count = map.getOrElse(word, 0L) + 1
    map.update(word, count)
  }

  //Driver合并多个累加器的值
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
    val map2: mutable.Map[String, Long] = other.value
    map2.foreach {
      case (word, cnt) => {
        val count = map.getOrElse(word, 0L) + cnt
        map.update(word, count)
      }
    }
  }

  override def value: mutable.Map[String, Long] = map
}
