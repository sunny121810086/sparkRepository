package sparkSQL.dataFrame

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object DataFrameV3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val df01: DataFrame = spark.read.json("src\\main\\resources\\dataFrame\\ads_recom_theme_user_oper_data_dm.json")

    //DataFrame转换成DataSet
    import spark.implicits._
    val ds01: Dataset[UserOper] = df01.as[UserOper]
    ds01.show()

    //DataSet转换成DataFrame
    //DataFrame其实是Dataset的Row类型--type DataFrame = Dataset[Row]
    val df02: DataFrame = ds01.toDF()
    val df03: Dataset[Row] = ds01.toDF()
    df03.show()

    spark.stop() //释放资源
  }
}

case class UserOper(up_id: String, oper_id: String, oper_type: String, item_id: String, oper_occur_time: String)
