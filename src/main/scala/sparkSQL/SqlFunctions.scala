package sparkSQL

import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SqlFunctions {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val inputDF = spark.read.format("text").load("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\sqlfunctions\\sort_array.txt")

    import spark.implicits._
    val userActionInfoDF = inputDF.map {
      case Row(line: String) => {
        val arr = line.split("\\,")
        val device_id = arr(0)
        val theme_id = arr(1)
        val theme_ver = arr(2)
        val oper_type = arr(3)
        val oper_date = arr(4)
        (device_id, theme_id, theme_ver, oper_type, oper_date)
      }
    }.toDF("device_id", "theme_id", "theme_ver", "oper_type", "oper_date")

    userActionInfoDF.createTempView("userActionInfoTable")

    val sql01 =
      """
        |select
        |    device_id,
        |    theme_id,
        |    theme_ver,
        |    concat_ws('|',sort_array(collect_set(oper_type))) as oper_type,
        |    oper_date
        |from userActionInfoTable
        |group by device_id,theme_id,theme_ver,oper_date
      """.stripMargin

    val sql02 = "select from_unixtime(unix_timestamp('20200919','yyyyMMdd'),'yyyy-MM-dd')"
    val sql03 = "select to_date('2020-06-07 11:11:11','yyyy-MM-dd HH:mm:ss')"
    val sql04 = "select date_format('2020-06-06 11:11:11','yyyy-MM')"
    val sql05 = "select datediff('2020-09-19',from_unixtime(unix_timestamp('20200915','yyyyMMdd'),'yyyy-MM-dd'))"
    spark.sql(sql03).show()
  }
}
