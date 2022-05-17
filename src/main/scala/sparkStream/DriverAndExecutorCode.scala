package sparkStream

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object DriverAndExecutorCode {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
    val ssc = new StreamingContext(sc, Seconds(5))

    val inputDstream: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.226.88", 6666)

    //TODO 代码1 --> Driver端执行 1次
    inputDstream.transform {
      rdd => {
        //TODO 代码2 --> Driver端执行 (每采集一次数据，执行一次。如：从redis读取黑名单可以在这里处理)
        rdd.map {
          case line => {
            //TODO 代码3 --> Executor端执行 (不同的Executor都会执行这段代码)
          }
        }
      }
    }

    inputDstream.map {
      case line => {
        //TODO 代码4 --> Executor端执行 (不同的Executor都会执行这段代码)
      }
    }

    /**
      * 数据库连接位置选择：
      * 1.根据闭包原则，Driver端对象需要序列化才能通过网络传入到Executor端，
      *   由于连接不能被序列化，所以不能在Driver端创建
      * 2.在foreach中创建，每一条数据都会创建一个连接，造成资源浪费
      * 3.使用foreachPartition算子，而RDD算子中的函数是在Executor端执行的，
      *  所以在分区中创建较好。
      **/
    inputDstream.foreachRDD {
      rdd => {
        //TODO Driver端执行，数据库连接不能被序列化，因此不能在这里创建
        rdd.foreachPartition {
          iter => {
            //TODO  这里创建数据库连接，如redis、mysql等
            iter.foreach {
              x => {
                //TODO 写入数据库
              }
            }
            //TODO 关闭连接
          }
        }
      }
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
