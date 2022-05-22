package sparkSQL.dataFrame

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object DataFrameV1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[*]")
      .getOrCreate()

    val df01: DataFrame = spark.read.json("src\\main\\resources\\dataFrame\\ads_recom_theme_user_oper_data_dm.json")

    //SQL语法
    df01.createTempView("ads_recom_theme_user_oper_dm")

    val sql01 =
      """
        |select
        |    up_id
        |    ,oper_id
        |    ,oper_type
        |    ,item_id
        |    ,oper_occur_time
        |    ,date_format(oper_occur_time,'yyyyMMdd') AS oper_date
        |from ads_recom_theme_user_oper_dm
      """.stripMargin

    val df02: DataFrame = spark.sql(sql01)
    df02.show()

    //DSL语法
    val ds01: Dataset[Row] = df01.where("oper_type='pay'")

    //DataFrame其实是Dataset的Row类型--type DataFrame = Dataset[Row]
    val ds02: DataFrame = df01.groupBy("up_id").count()
    val ds03: Dataset[Row] = df01.groupBy("up_id").count()

    spark.stop() //释放资源
  }
}
