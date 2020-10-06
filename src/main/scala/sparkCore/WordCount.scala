package sparkCore

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("yarn")
      .getOrCreate()

    val sc = spark.sparkContext

    val inputPath = args(0)
    val outputPath = args(1)

    val lineRDD = sc.textFile(inputPath)

    val arrayRDD = lineRDD.map {
      line => {
        val arr = line.split(" ")
        arr
      }
    }

    val tupleRDD = arrayRDD.flatMap(array => {
      array.map {
        x => (x,1)
      }
    })

    val resultRDD = tupleRDD.reduceByKey(_+_)

    resultRDD.saveAsTextFile(outputPath)
  }

}
