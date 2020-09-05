package sparkStream

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowOperation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext
    val ssc = new StreamingContext(sc,Seconds(5))

    val inputDstream: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.226.88",6666)

    //窗口持续时间--15s; 滑动时间--10s
    //默认每个批次数据生成一个RDD，然后封装成一个Dstream，如果是窗口操作,一个Dstream应该包含多个RDD,
    //这里窗口持续时间为15s，应该生成3个RDD，然后封装成一个Dstream，
    //所以一个Dstream包含一个或多个RDD，可以通过foreachRDD()遍历所有RDD
    val windowDstream = inputDstream.window(Seconds(15),Seconds(10))

    val tupleDstream: DStream[((String, String), Int)] = windowDstream.map {
      line => {
        val arr = line.split(" ")
        val provence = arr(0)
        val city = arr(1)
        val area = arr(2)
        ((provence,city),1)
      }
    }

    val reduceByKeyDstream = tupleDstream.reduceByKey(_+_)


    reduceByKeyDstream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
