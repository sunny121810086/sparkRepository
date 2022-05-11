package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession


object RDDTransformV3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 4)

    /**
     *  def mapPartitionsWithIndex[U: ClassTag](
      *     f: (Int, Iterator[T]) => Iterator[U],
      *     preservesPartitioning: Boolean = false): RDD[U]= withScope {
      *   val cleanedF = sc.clean(f)
      *   new MapPartitionsRDD(
      *     this,
      *     (context: TaskContext, index: Int, iter: Iterator[T]) => cleanedF(index, iter),
      *     preservesPartitioning)
      * }
     **/

    //操作指定分区的数据
    val rdd01A = rdd01.mapPartitionsWithIndex {
      (index, iter) => {
        if (index % 2 == 1) {
          iter.map((_, s"${index}_A"))
        } else {
          //Nil.iterator
          iter.map((_, s"${index}_B"))
        }
      }
    }

    println(rdd01A.collect.toBuffer)

    sc.stop()
  }
}
