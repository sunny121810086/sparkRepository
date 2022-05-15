package sparkCore.rddTransform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object WordCountV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
    val lineRDD: RDD[String] = sc.makeRDD(List("hello java", "hello scala", "hello spark"))
    //wordcount实现方式一
    val wordcount1: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).groupByKey().map(tuple => (tuple._1, tuple._2.sum))
    println("wordcount1: " + wordcount1.collect().toBuffer)
    //wordcount实现方式二
    val wordcount2: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).groupByKey().mapValues(it => it.sum)
    println("wordcount2: " + wordcount2.collect().toBuffer)
    //wordcount实现方式三
    val wordcount3: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    println("wordcount3: " + wordcount3.collect().toBuffer)
    //wordcount实现方式四
    val wordcount4: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).foldByKey(0)(_ + _)
    println("wordcount4: " + wordcount4.collect().toBuffer)
    //wordcount实现方式五
    val wordcount5: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).combineByKey(x => x, (a: Int, b: Int) => a + b, (m: Int, n: Int) => m + n)
    println("wordcount5: " + wordcount5.collect().toBuffer)
    //wordcount实现方式六
    val wordcount6: RDD[(String, Int)] = lineRDD.flatMap(_.split(" ")).map((_, 1)).aggregateByKey(0)((x: Int, y) => x + y, (m: Int, n: Int) => m + n)
    println("wordcount6: " + wordcount6.collect().toBuffer)
    //wordcount实现方式七
    val wordcount7: collection.Map[String, Long] = lineRDD.flatMap(_.split(" ")).countByValue()
    println("wordcount7: " + wordcount7)
    //wordcount实现方式八
    val wordcount8: collection.Map[String, Long] = lineRDD.flatMap(_.split(" ")).map((_, 1)).countByKey()
    println("wordcount8: " + wordcount8)

    sc.stop()
  }
}
