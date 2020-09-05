package sparkStream

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object DriverAndExecutorCode {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext
    val ssc = new StreamingContext(sc, Seconds(5))

    val inputDstream: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.226.88", 6666)

    //TODO 代码1 --> Driver端执行 1次
    inputDstream.transform {
      rdd => {
        //TODO 代码2 --> Driver端执行 (每采集一次数据，执行一次。如：从redis读取黑名单可以在这里处理)
        rdd.map {//RDD的算子中传入函数在Driver端执行
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

    //数据库连接位置选择
    //1.不能在Driver端,因为连接不能序列化,传入到Executor端
    //2.foreach中创建，造成资源浪费
    //3.增加foreachPartition,在分区创建

    inputDstream.foreachRDD {
      rdd => {
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
