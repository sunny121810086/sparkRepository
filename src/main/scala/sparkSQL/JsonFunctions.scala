package sparkSQL

import org.apache.spark.sql.{DataFrame, SparkSession}

object JsonFunctions {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext

    val lineRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\ods_event_log.txt")

    import spark.implicits._
    val dataFrame1: DataFrame = lineRDD.toDF("line")
    dataFrame1.createTempView("ods_event_log")

    val sql01 =
       """
        |select
        |    split(line,'\\|')[0] server_time,
        |    get_json_object(split(line,'\\|')[1],'$.et') event_json
        |from ods_event_log
      """.stripMargin

    spark.sql(sql01).createTempView("ods_event_log_tmp1")

    val sql02 =
      """
        |select
        |    server_time,
        |    tmp.event_json
        |from ods_event_log_tmp1
        |lateral view explode(split(regexp_replace(regexp_extract(event_json,'^\\[(.+)\\]$',1),'\\}\\,\\{','\\}\\|\\|\\{'),'\\|\\|'))tmp as event_json
      """.stripMargin

    spark.sql(sql02).createTempView("ods_event_log_tmp2")

    val servertime = "1592116043890"
    //错误写法
    val sql03 =
      """
        |select
        |    server_time,
        |    get_json_object(event_json,'$.en') event_name,
        |    event_json
        |from ods_event_log_tmp2
        |where server_time = ${servertime}
      """.stripMargin

    //正确写法
    val sql04 =
      s"""
         |select
         |    server_time,
         |    get_json_object(event_json,'$$.en') event_name,
         |    event_json
         |from ods_event_log_tmp2
         |where server_time = ${servertime}
       """.stripMargin

    spark.sql(sql04).show()
  }

}
