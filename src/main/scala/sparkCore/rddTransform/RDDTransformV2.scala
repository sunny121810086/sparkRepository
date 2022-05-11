package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession


object RDDTransformV2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4),4)

    /**
     *  def mapPartitions[U: ClassTag](
      *     f: Iterator[T] => Iterator[U],
      *     preservesPartitioning: Boolean = false): RDD[U] = withScope {
      *   val cleanedF = sc.clean(f)
      *   new MapPartitionsRDD(
      *       this,
      *       (context: TaskContext, index: Int, iter: Iterator[T]) => cleanedF(iter),
      *       preservesPartitioning)
      * }
     **/

    //以分区为单位进行批量处理
    val rdd01A = rdd01.mapPartitions {
      iter => {
        println("*****************") //多少个分区执行多少次
        iter.map(_ * 10)
      }
    }

    val rdd02 = sc.makeRDD(List(1,2,3,4,5,6,7,8),4)
    //获取每个分区最大值
    val rdd02A = rdd02.mapPartitions {
     // iter => iter.toList.sortWith(_ > _).take(1).iterator
      iter => List(iter.max).iterator
    }
    val rdd02B = rdd02A.mapPartitionsWithIndex {
      (index, iter) => {
        iter.foreach {
          max_val => println(s"index：$index max_val：$max_val")
        }
        iter
      }
    }

    rdd02B.collect()

    sc.stop()
  }
}
