package sparkCore.accumulator

import org.apache.spark.sql.SparkSession

object AccumulatorV2 {
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

    rdd01.foreach(sumAcc.add(_))

    println(sumAcc.value) //15

    sc.stop()
  }
}
