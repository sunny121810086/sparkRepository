package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession


object RDDTransformV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4))
    /**
      * def map[U: ClassTag](f: T => U): RDD[U] = withScope {
      *   val cleanF = sc.clean(f)
      *   new MapPartitionsRDD[U, T](this, (context, pid, iter) => iter.map(cleanF))
      * }
     **/
    
    val rdd01A = rdd01.map(func01)
    val rdd01B = rdd01.map(x => x*10) //匿名函数方式，x类型可推导，无需再写类型

    //分布式执行测试
    val rdd02 = sc.makeRDD(List(1,2,3,4,5),2)
    val rdd02A = rdd02.map {
      x => {
        println(s"aaaaaaaaaaaaa：$x")
        x*10
      }
    }
    val rdd02B = rdd02A.map {
      x => {
        println(s"bbbbbbbbbbbbb：$x")
        x
      }
    }
    rdd02B.collect()

    sc.stop()
  }

  val func01 = (x: Int) => x*10
}
