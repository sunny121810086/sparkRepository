package sparkCore.rddTransform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDTransformV13 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    /**
      * PairRDDFunctions类中的方法：
      * def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,
      * combOp: (U, U) => U): RDD[(K, U)] = self.withScope {
      * aggregateByKey(zeroValue, defaultPartitioner(self))(seqOp, combOp)
      * }
      * 说明：aggregateByKey适合分区内和分区间聚合逻辑不同时使用
      * zeroValue: U 表示初始值，用于和分区内的第一个value进行聚合操作，也决定最终key对应的value值为U类型
      * (U, V) => U  对同一个分区内相同key的value进行聚合操作，并返回初始值的类型
      * combOp: (U, U) => U 分区间相同key的value聚合操作
      *
      * foldByKey--分区内和分区间聚合逻辑相同时使用
      * def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)] = self.withScope {
      * foldByKey(zeroValue, defaultPartitioner(self))(func)
      * }
      **/
    val rdd01 = sc.makeRDD(List(("AA", 2), ("AA", 4), ("AA", 8), ("BB", 3), ("BB", 9), ("BB", 27)), 3)

    val rdd01A = rdd01.aggregateByKey(0)(
      (v1, v2) => math.max(v1, v2),
      (v3, v4) => v3 + v4
    )

    //分区内相同key的value用:拼接，分区间相同key用#拼接
    val rdd01B = rdd01.aggregateByKey("@")(
      (v1, v2) => s"$v1:$v2",
      (v3, v4) => s"$v3#$v4"
    )

    //计算相同key的平均值，如：AA--14/3 BB=39/3
    //初始值(0,0)的类型确定，将决定最终key对应的value值的类型
    val rdd01C: RDD[(String, (Int, Int))] = rdd01.aggregateByKey((0, 0))(
      (tup, v) => (tup._1 + 1, tup._2 + v),
      (tup1, tup2) => (tup1._1 + tup2._1, tup1._2 + tup2._2)
    )
    val rdd01D = rdd01C.map {
      x => {
        x match {
          case tup: (String, (Int, Int)) => (tup._1, tup._2._2.toDouble / tup._2._1.toDouble)
        }
      }
    }

    val rdd01E = rdd01C.map(tup => (tup._1, tup._2._2 / tup._2._1.toDouble))
    println(rdd01A.collect.toBuffer) //ArrayBuffer((BB,30), (AA,12))
    println(rdd01B.collect.toBuffer) //ArrayBuffer((BB,:3#:9:27), (AA,:2:4#:8))
    println(rdd01C.collect.toBuffer) //ArrayBuffer((BB,(3,39)), (AA,(3,14)))
    println(rdd01D.collect.toBuffer) //ArrayBuffer((BB,13.0), (AA,4.666666666666667))
    println(rdd01E.collect.toBuffer) //ArrayBuffer((BB,13.0), (AA,4.666666666666667))

    //分区内和分区间聚合逻辑相同时使用
    val rdd02A = rdd01.foldByKey(0)(_ + _)
    println(rdd02A.collect.toBuffer) //ArrayBuffer((BB,39), (AA,14))

    sc.stop()
  }
}