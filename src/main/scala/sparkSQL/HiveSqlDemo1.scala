package sparkSQL

import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object HiveSqlDemo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val inputDF = spark.read.format("text").load("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\healthy.txt")
    val inputDF2 = spark.read.format("text").load("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\healthy2.txt")

    import spark.implicits._
    val userActionInfoDF = inputDF.map {
      case Row(line: String) => {
        val arr = line.split("\\|")
        val up_id = arr(0)
        val item_id = arr(1)
        val opper_occur_time = arr(2)
        val time_interval = arr(3)
        val oper_days = arr(4)
        val weight = arr(5)
        (up_id, item_id, opper_occur_time, time_interval, oper_days,weight)
      }
    }.toDF("up_id", "item_id", "opper_occur_time", "time_interval", "oper_days","weight")

    val userActionInfoDF2 = inputDF2.map {
      case Row(line: String) => {
        val arr = line.split("\\|")
        val up_id = arr(0)
        val item_id = arr(1)
        val opper_occur_time = arr(2)
        val weight = arr(3)
        (up_id, item_id, opper_occur_time,weight)
      }
    }.toDF("up_id", "item_id", "opper_occur_time","weight")

    userActionInfoDF2.createTempView("ads_rcm_healthy_item_pref_dm")

    val sql01 =
      """
        |select
        |    up_id
        |    ,item_id
        |    ,opper_occur_time
        |    ,time_interval
        |    ,oper_days
        |    ,weight
        |from ads_rcm_healthy_item_pref_dm
      """.stripMargin

    val sql02 =
      """
        |select
        |    up_id
        |    ,item_id
        |    ,weight
        |    ,opper_occur_time
        |    ,cast(date_format(opper_occur_time,'HH') as int)  AS time_interval
        |    ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |from ads_rcm_healthy_item_pref_dm
      """.stripMargin

    val sql03 =
      """
        |select
        |    up_id
        |    ,item_id
        |    ,weight
        |    ,opper_occur_time
        |    ,time_interval
        |    ,oper_days
        |from
        |    (
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,opper_occur_time
        |        ,cast(date_format(opper_occur_time,'HH') as int) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |
        |    union
        |
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,opper_occur_time
        |        ,if(cast(date_format(opper_occur_time,'HH') as int)=0,23,cast(date_format(opper_occur_time,'HH') as int)-1) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |
        |    union
        |
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,opper_occur_time
        |        ,if(cast(date_format(opper_occur_time,'HH') as int)=23,0,cast(date_format(opper_occur_time,'HH') as int)+1) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |    )t1
        |where item_id='14'
      """.stripMargin

    val sql04 =
      """
        |select
        |    up_id
        |    ,item_id
        |    ,time_interval
        |    ,sum(weight/(oper_days+1)) AS score
        |from
        |    (
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,cast(date_format(opper_occur_time,'HH') as int) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |
        |    union
        |
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,if(cast(date_format(opper_occur_time,'HH') as int)=0,23,cast(date_format(opper_occur_time,'HH') as int)-1) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |
        |    union
        |
        |    select
        |        up_id
        |        ,item_id
        |        ,weight
        |        ,if(cast(date_format(opper_occur_time,'HH') as int)=23,0,cast(date_format(opper_occur_time,'HH') as int)+1) AS time_interval
        |        ,datediff('2022-04-16',date_format(opper_occur_time,'yyyy-MM-dd')) AS oper_days
        |    from ads_rcm_healthy_item_pref_dm
        |    )t1
        |group by up_id,item_id,time_interval
      """.stripMargin

    val sql05 =
      """
        |select
        |    pt_d
        |    ,date_format(pt_d,'MM')  as month
        |    ,pay_amt
        |    ,sum(pay_amt)over(partition by date_format(pt_d,'MM')) as month_amt
        |from
        |(
        |    select '2022-05-20' as pt_d,100 as pay_amt
        |    union all
        |    select '2022-05-21' as pt_d,200 as pay_amt
        |    union all
        |    select '2022-05-22' as pt_d,300 as pay_amt
        |    union all
        |    select '2022-06-20' as pt_d,400 as pay_amt
        |    union all
        |    select '2022-06-21' as pt_d,500 as pay_amt
        |    union all
        |    select '2022-06-22' as pt_d,600 as pay_amt
        |)t1
      """.stripMargin

    spark.sql(sql05).show()
  }
}
