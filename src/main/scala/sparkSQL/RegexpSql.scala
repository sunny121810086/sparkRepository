package sparkSQL

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RegexpSql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext

    val lineRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\regexp\\tag_info.txt")

    val tupleRDD: RDD[(String, String)] = lineRDD.map {
      line => {
        val arr = line.split("\\|\\|")
        (arr(0), arr(1))
      }
    }

    import spark.implicits._
    val dataFrame1 = tupleRDD.toDF("pt_d", "tag")
    dataFrame1.createTempView("tag_info")


    //.+,[ ]?,.+   匹配字符串中间空值
    //^,.+  匹配字符串前面空值
    //.+,[,]?$  匹配字符串结尾空值
    val sql01 =
      """
        |select
        |    pt_d,
        |    tag
        |from tag_info
        |where tag regexp ('.+,[ ]?,.+|^,.+|.+,[,]?$')
      """.stripMargin

    //截取当前字符串最后一位两种方式
    val sql02 = "select substr('qwerdf',length('qwerdf'),1)"
    val sql03 = "select substr('qwerdf',-1,1)"

    //匹配18位身份证号
    val sql04 =
      """
        |select regexp_extract('342601199610014321','^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$',0) as idCard,--匹配身份证号
        |regexp_extract('http://www.baidu.com','[a-zA-z]+://[^\s]*',0) as url --匹配网址URL
      """.stripMargin

    //返回true
    val sql05 =
      """
        |select '星空|射手座' regexp '星空|星辰大海|仙女座'
      """.stripMargin

    val sql06 =
      """
        |select
        |up_id
        |,member_stage
        |,stage
        |,if(split(res,'_')[1]='','-1',split(res,'_')[1]) as res
        |from
        |(
        |    select
        |    up_id
        |    ,member_stage
        |    ,if(member_stage regexp '^\\[".+"\\]$',member_stage,'-1') as stage
        |    from
        |        (
        |            select
        |            '1001' as up_id
        |            ,'["UserMemberStage_0","UserMemberStage_5"]' as member_stage
        |
        |            union all
        |
        |            select
        |            '1002' as up_id
        |            ,'["UserMemberStage_0"]' as member_stage
        |
        |            union all
        |
        |            select
        |            '1003' as up_id
        |            ,'[]' as member_stage
        |
        |            union all
        |
        |            select
        |            '1004' as up_id
        |            ,'[""]' as member_stage
        |
        |            union all
        |
        |            select
        |            '1005' as up_id
        |            ,NULL as member_stage
        |
        |            union all
        |
        |            select
        |            '1006' as up_id
        |            ,'' as member_stage
        |        )t1
        |)t2
        |lateral view explode(split(regexp_replace(regexp_extract(stage,'^\\[(.+)\\]$',1),'"',''),',')) tmp AS res
      """.stripMargin

    val sql07 =
      """
        |select
        |up_id
        |,regexp_extract(member_stage,'^\\["(.+)"\\]$',1) stage
        |from
        |(
        |    select
        |    '1001' as up_id
        |    ,'["Stage_0"]' as member_stage
        |
        |    union all
        |
        |    select
        |    '1002' as up_id
        |    ,'[""]' as member_stage
        |)t1
      """.stripMargin

    spark.sql(sql07).show()

    spark.stop()
  }

}
