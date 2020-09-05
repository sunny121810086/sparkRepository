package sparkCore

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RddFunctions {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd = sc.makeRDD(List(1,2,3,4,5,6))
    val pairRdd = sc.makeRDD(List(("a",1),("b",2),("b",3),("c",4),("c",5)),2)

    //glom--将不同分区数据加载到对应的数组中
    val glomRdd: RDD[Array[(String, Int)]] = pairRdd.glom()
    glomRdd.foreach(arr => println(arr.toBuffer))

    //groupBy--分组。根据传入函数的返回值进行分组，并将相同返回值作为key，对应的value放入一个迭代器
    val groupRdd1: RDD[(Int, Iterable[Int])] = rdd.groupBy(_ % 2)
    val groupRdd2: RDD[(Boolean, Iterable[Int])] = rdd.groupBy(_ < 5)
    println(groupRdd1.collect().toBuffer)
    println(groupRdd2.collect().toBuffer)

    //filter--过滤。根据传入函数的返回值进行过滤，如果为true则保留
    val filterRdd: RDD[Int] = rdd.filter(_ % 2 ==0)

    //withReplacement: Boolean -- 抽样是否放回，true为放回
    //fraction: Double -- 可以看出比例
    //seed: Long = Utils.random.nextLong --随机数生成器种子
    val sampleRdd = rdd.sample(true,0.9)
    val coalesceRdd = rdd.coalesce(2,true)

    //将每个分区相同key的最大值求和
    //zeroValue -- 指定每个分区中每种key的初始值
    //seqOp -- 分区内相同key的value进行操作
    //combOp -- 分区间相同key的value进行操作
    val aggregateByKeyRdd1: RDD[(String, Int)] = pairRdd.aggregateByKey(0)(math.max(_,_),_+_)
    val aggregateByKeyRdd2: RDD[(String, Int)] = pairRdd.aggregateByKey(10)(math.max(_,_),_+_)
    //def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,combOp: (U, U) => U): RDD[(K, U)]
    //ClassTag表示运行时类型推断，当zeroValue初始值给定后，类型也随之确定，则后面涉及的U类型也可以推断

    //def combineByKey[C](createCombiner: V => C, //未使用ClassTag类进行运行是推断
    //                    mergeValue: (C, V) => C, //传参时需要指定具体类型
    //                    mergeCombiners: (C, C) => C): RDD[(K, C)]

    //计算相同key的平均值
    //createCombiner -- 分区内每种key对应value转换
    //mergeValue -- 分区内，将createCombiner()的结果与相同key对应的value合并操作
    //mergeCombiners -- 分区间，分区间相同key对应的value进行操作
    val combineByKeyRdd: RDD[(String, (Int, Int))] = pairRdd.combineByKey(value => (value, 1),
      (tuple2: (Int, Int), thatValue: Int) => (tuple2._1 + thatValue, tuple2._2 + 1),
      (partCombine1: (Int, Int), partCombine2: (Int, Int)) => (partCombine1._1 + partCombine2._1, partCombine1._2 + partCombine2._2))

    val avgRdd1: RDD[(String, Double)] = combineByKeyRdd.map {
      case (key, (value1,value2)) => (key, value1 / value2.toDouble)
    }

    val avgRdd2: RDD[(String, Double)] = combineByKeyRdd.map {
      _ match {
        case (key, (value1, value2)) => (key, value1 / value2.toDouble)
      }
    }

    println(avgRdd1.collect().toBuffer)
    println(avgRdd2.collect().toBuffer)

  }
}
