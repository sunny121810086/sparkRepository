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

    spark.sql(sql04).show()

    spark.stop()
  }

}
