package sparkStream

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamDataFrameOperation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val lineDF: DataFrame = spark.readStream
      .format("socket")
      .option("host", "192.168.226.88")
      .option("port", 6666)
      .option("includeTimestamp", false) //给产生的数据自动添加时间戳
      .load()



    lineDF.writeStream
      .format("console")
      .outputMode("update")
      .start()
      .awaitTermination()

  }

}
