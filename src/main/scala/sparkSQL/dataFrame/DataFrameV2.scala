package sparkSQL.dataFrame

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DataFrameV2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd01 = sc.textFile("src\\main\\resources\\dataFrame\\ads_recom_theme_user_oper_data_dm.txt")

    //RDD转换成DataFrame
    import spark.implicits._
    val df01: DataFrame = rdd01.map {
      line => {
        val arr = line.split(",")
        val up_id = arr(0)
        val oper_id = arr(1)
        val oper_type = arr(2)
        val item_id = arr(3)
        val oper_occur_time = arr(4)
        (up_id,oper_id,oper_type,item_id,oper_occur_time)
      }
    }.toDF("up_id","oper_id","oper_type","item_id","oper_occur_time")

    df01.show()

    //DataFrame转换成RDD
    val rdd02: RDD[Row] = df01.rdd

    spark.stop() //释放资源
  }
}
