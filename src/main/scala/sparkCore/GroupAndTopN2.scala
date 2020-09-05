package sparkCore

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object GroupAndTopN2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext

    //时间戳 省份 城市 用户 广告
    val lineRDD: RDD[String] = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\advertisementInfo.txt")

    //(省_广告,1)
    val provenceAndAdvertisementRDD: RDD[(String, Long)] = lineRDD.map {
      line => {
        val provence = line.split(" ")(1)
        val advertisement = line.split(" ")(4)
        val key = provence + "_" + advertisement
        (key, 1L)
      }
    }

    //(省_广告,点击次数)
    val provenceAndAdvertiseReduceBykeyRDD: RDD[(String, Long)] = provenceAndAdvertisementRDD.reduceByKey(_+_)

    val groupByRDD: RDD[(String, Iterable[(String, Long)])] = provenceAndAdvertiseReduceBykeyRDD.groupBy(_._1.split("_")(0))

    val topnRDD: RDD[(String, List[(String, Long)])] = groupByRDD.map {
      x => {
        val provence = x._1
        val list = x._2.toList
        //sortBy默认为升序,需要对结果reverse实现降序
        val descSort = list.sortBy(_._2).reverse.take(2)
       // val descSort: List[(String, Long)] = list.sortWith(_._2 > _._2).take(1)
        (provence, descSort)
      }
    }

    println(topnRDD.collect().toBuffer)

    //每天、每省、每用户点击广告次数
    val dayProvenceUserRDD: RDD[((String, String, String), Long)] = lineRDD.map {
      line => {
        val arr = line.split(" ")
        val day = dayFormat(arr(0).toLong)
        val provence = arr(1)
        val uid = arr(4)
        ((day, provence, uid), 1L)
      }
    }.reduceByKey(_+_)

    val groupByDayRDD = dayProvenceUserRDD.map {
      case ((day, provence, uid), count)
      => {
        (day, (day, provence, uid, count))
      }
    }.groupByKey()

    val sortRDD = groupByDayRDD.map {
      case (day, iter) => {
        val sortList = iter.toList.sortWith(_._4 > _._4)
        (day,sortList)
      }
    }

    println(sortRDD.collect().toBuffer)

  }

  def dayFormat(longTime: Long): String = {
    val date = new Date(longTime)
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val day = dateFormat.format(date)
    day
  }
}
