package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV15 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

      val rdd01 = sc.makeRDD(List(("AA", 1), ("BB", 2), ("CC", 3), ("DD", 4)), 2)
      val rdd02 = sc.makeRDD(List(("AA", 1.1), ("BB", 2.2), ("CC", 3.3), ("CC", 4.4)), 2)

     val rdd01A = rdd01.join(rdd02)
    val rdd01B = rdd01.leftOuterJoin(rdd02)
    println(rdd01A.collect.toBuffer) //ArrayBuffer((BB,(2,2.2)), (CC,(3,3.3)), (CC,(3,4.4)), (AA,(1,1.1)))
    println(rdd01B.collect.toBuffer) //ArrayBuffer((DD,(4,None)), (BB,(2,Some(2.2))), (CC,(3,Some(3.3))), (CC,(3,Some(4.4))), (AA,(1,Some(1.1))))

    sc.stop()
  }
}