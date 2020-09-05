package sparkSQL



import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}

object GroupAndTopNSql2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val scoreDF = spark.read.format("text").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\studentScore.txt")
    val sinfoDF = spark.read.format("text").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\studentInfo.txt")
    val tinfoDF = spark.read.format("text").load("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\teacherInfo.txt")

    import spark.implicits._

    val scoreTupleDF = scoreDF.map {
      case Row(oneField: String) => {
        val arr = oneField.split(" ")
        val snumber = arr(0).toInt
        val sname = arr(1)
        val chineseScore = arr(2)
        val mathScore = arr(3)
        val englishScore = arr(4)
        val physicsScore = arr(5)
        val chemistryScore = arr(6)
        val biologyScore = arr(7)
        (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore)
      }
    }.toDF("snumber", "sname", "chineseScore", "mathScore", "englishScore", "physicsScore", "chemistryScore", "biologyScore")
    //DataFrame --> RDD
    val value: RDD[(Int, String, String, String, String, String, String, String)] = scoreTupleDF.rdd.map {
      case Row(a: Int, b: String, c: String, d: String, e: String, f: String, g: String, h: String) => {
        (a, b, c, d, e, f, g, h)
      }
    }
    println(value.collect().toBuffer)
    val tupleRDD: RDD[(Int, String, String, String, String, String, String, String)] = scoreTupleDF.as[(Int, String, String, String, String, String, String, String)].rdd
    //println(tupleRDD.collect().toBuffer)

    import org.apache.spark.sql.functions._
    //DataFrame只有Schema信息,没有数据类型信息,用map操作时需要指定Row中参数类型
    scoreTupleDF.map {
      case Row(snumber: Int, sname: String, chineseScore: String, mathScore: String, englishScore: String, physicsScore: String, chemistryScore: String, biologyScore: String)
      => {
        val subject = chineseScore + "|" +
          mathScore + "|" +
          englishScore + "|" +
          physicsScore + "|" +
          chemistryScore + "|" +
          biologyScore
        (snumber, sname, subject)
      }
    }.toDF("snumber","sname","subject")

    scoreTupleDF
      .select($"snumber",$"sname",concat_ws("|",col("chineseScore"),$"mathScore",col("englishScore"),col("physicsScore"),$"chemistryScore",$"biologyScore").as("subject"))
      //.show()

  }
}
