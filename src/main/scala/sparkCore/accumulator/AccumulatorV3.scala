package sparkCore.accumulator

import org.apache.spark.sql.SparkSession

object AccumulatorV3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1, 2, 3, 4, 5))

    //分布式共享只写变量--累加器
    val sumAcc = sc.longAccumulator("sumAcc")

    val rdd02 = rdd01.map {
      x => {
        sumAcc.add(x)
        x
      }
    }

    //累加器一般在Action类型算子中使用，否则可能出现重复累加的情况
    rdd02.collect
    rdd02.collect
    println(sumAcc.value) //这里collect触发了两个job，累加器会被重复计算，所有输出30

    sc.stop()
  }
}
