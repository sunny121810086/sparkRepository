package sparkSQL.dataFrame

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object DataFrameV4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd01 = sc.textFile("src\\main\\resources\\dataFrame\\ads_recom_theme_user_oper_data_dm.txt")

    val rdd02: RDD[UserOperV2] = rdd01.map {
      line => {
        val arr = line.split(",")
        val up_id = arr(0)
        val oper_id = arr(1)
        val oper_type = arr(2)
        val item_id = arr(3)
        val oper_occur_time = arr(4)
        UserOperV2(up_id, oper_id, oper_type, item_id, oper_occur_time)
      }
    }

    val rdd03: RDD[(String, String, String, String, String)] = rdd01.map {
      line => {
        val arr = line.split(",")
        val up_id = arr(0)
        val oper_id = arr(1)
        val oper_type = arr(2)
        val item_id = arr(3)
        val oper_occur_time = arr(4)
        (up_id, oper_id, oper_type, item_id, oper_occur_time)
      }
    }

    //将RDD转换成RDD[样例类]，再转换成Dataset[样例类]
    import spark.implicits._
    val ds01: Dataset[UserOperV2] = rdd02.toDS()
    ds01.show()

    //RDD[] 转换成 Dataset[]
    val ds02: Dataset[(String, String, String, String, String)] = rdd03.toDS()

    //Dataset转换成RDD[样例类]
    val rdd04: RDD[UserOperV2] = ds01.rdd

    spark.stop() //释放资源
  }
}

case class UserOperV2(up_id: String, oper_id: String, oper_type: String, item_id: String, oper_occur_time: String)