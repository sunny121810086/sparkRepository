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

    spark.sql(sql01).show()
  }
}
