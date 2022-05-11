package sparkCore.rddTransform

import org.apache.spark.sql.SparkSession

object RDDTransformV6 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.makeRDD(List(1,2,3,4,5,6,7,8,9,10))

    /**
      * def sample(
      *     withReplacement: Boolean,
      *     fraction: Double,
      *     seed: Long = Utils.random.nextLong): RDD[T] = {
      *   require(fraction >= 0,
      *     s"Fraction must be nonnegative, but got ${fraction}")
      *
      * withScope {
      *   require(fraction >= 0.0, "Negative fraction value: " + fraction)
      *   if (withReplacement) {
      *     new PartitionwiseSampledRDD[T, T](this, new PoissonSampler[T](fraction), true, seed)
      *   } else {
      *     new PartitionwiseSampledRDD[T, T](this, new BernoulliSampler[T](fraction), true, seed)
      *   }
      *  }
      * }
      * 参数说明：
      * 1.withReplacement--抽取数据后是否将数据放回，true--放回，false--不放回
      * 2.fraction--数据源中每个数据被抽取的概率
      * 3.seed--抽取数据时，随机算法种子，默认是当前系统时间。
      *         当seed使用默认值时，每次抽取的数据是不同的。
     **/

    val rdd01A = rdd01.sample(
      false,
      0.5,
      1
    )
    println(rdd01A.collect.mkString(","))
  }
}
