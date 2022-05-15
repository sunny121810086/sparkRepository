package sparkCore.accumulator

import org.apache.spark.sql.SparkSession

object AccumulatorV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1, 2, 3, 4, 5))

    var sum = 0 //Driver端执行

    //foreach算子传入的函数，计算逻辑在Executor端执行
    rdd01.foreach {
      x => {
        println(s"Executor... x=$x sum=$sum")
        sum += x
      }
    }

    println(s"Driver... sum=$sum")
    sc.stop()

    /**
      * 输出结果：
      * Executor... x=1 sum=0
      * Executor... x=4 sum=0
      * Executor... x=2 sum=0
      * Executor... x=3 sum=0
      * Executor... x=5 sum=0
      * Driver... sum=0
      **/

  }
}
