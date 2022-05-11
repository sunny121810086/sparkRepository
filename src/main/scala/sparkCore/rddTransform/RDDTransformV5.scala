package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV5 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4,5,6,7),4)

    /**
     *  def groupBy[K](f: T => K)(implicit kt: ClassTag[K]): RDD[(K, Iterable[T])] = withScope {
      *   groupBy[K](f, defaultPartitioner(this))
      * }
     **/
    //将数据源中每一个数据进行分组判断，根据返回的key进行分组
    val rdd01A = rdd01.groupBy(groupFunc01)
    val rdd01B = rdd01.groupBy((num: Int) => num % 2) //匿名函数
    val rdd01C = rdd01.groupBy(_ % 2)

    //println(rdd01A.collect.toBuffer) //ArrayBuffer((0,CompactBuffer(2, 4, 6)), (1,CompactBuffer(1, 3, 5, 7)))

    val rdd02 = sc.makeRDD(List("华为_A","华为_B","苹果_C","苹果_D","小米_E","小米_F"),4)
    val rdd02A = rdd02.groupBy(groupFunc02)
    val rdd02B = rdd02.groupBy((phone: String) => phone.split("_")(0))
    val rdd02C = rdd02.groupBy(_.split("_")(0))
    println(rdd02C.collect.toBuffer) //ArrayBuffer((华为,CompactBuffer(华为_A, 华为_B)), (小米,CompactBuffer(小米_E, 小米_F)), (苹果,CompactBuffer(苹果_C, 苹果_D)))

  }

  val groupFunc01: Int => Int = num => {
    num % 2
  }

  def groupFunc02(phone: String): String = {
    phone.split("_")(0)
  }
}
