package sparkSQL

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object GroupAndTopN1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext

    //时间戳 省份 城市 用户 广告
    val lineRDD: RDD[String] = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\advertisementInfo.txt")

    //(省份,广告)
    val tupleRDD: RDD[(String, String)] = lineRDD.map {
      line => {
        val provence = line.split(" ")(1)
        val advertisement = line.split(" ")(4)
        (provence, advertisement)
      }
    }

    //(省份,Iterable(广告,1))
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = tupleRDD.map {
      case (provence, advertisement) => (provence, (advertisement, 1))
    }.groupByKey()

    //(省份,Map(广告,累计次数))
    val proAndAdCountRDD: RDD[(String, Map[String, Long])] = groupRDD.map {
      case (provence, iter) => {
        val groupByAd: Map[String, Iterable[(String, Int)]] = iter.groupBy(_._1)
        val advertiseCount: Map[String, Long] = groupByAd.map {
          case (ad, iter) => (ad, iter.size.toLong)
        }
        (provence, advertiseCount)
      }
    }


    val topnRDD = proAndAdCountRDD.map {
      x => {
        val provence = x._1
        val list: List[(String, Long)] = x._2.toList
        val descSort = list.sortBy(_._2).reverse.take(2)
        (provence, descSort)
      }
    }

    println(topnRDD.collect().toBuffer)

  }

}
