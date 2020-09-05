package sparkSQL

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object JoinTypeDSL {
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
    import scala.collection.mutable

    //学生成绩数据集
    val scoreTupleDS: Dataset[(Int, String, mutable.Map[String, Int])] = scoreDF.map {
      case Row(line: String) => {
        val map = mutable.Map[String,Int]()
        val arr = line.split(" ")
        val snumber = arr(0).toInt
        val sname = arr(1)
        val chinese = arr(2).split(":")(0)
        val chineseScore = arr(2).split(":")(1).toInt
        val math = arr(3).split(":")(0)
        val mathScore = arr(3).split(":")(1).toInt
        val english = arr(4).split(":")(0)
        val englishScore = arr(4).split(":")(1).toInt
        val physics = arr(5).split(":")(0)
        val physicsScore = arr(5).split(":")(1).toInt
        val chemistry = arr(6).split(":")(0)
        val chemistryScore = arr(6).split(":")(1).toInt
        val biology = arr(7).split(":")(0)
        val biologyScore = arr(7).split(":")(1).toInt

        map+=(chinese -> chineseScore,
            math -> mathScore,
            english -> englishScore,
            physics -> physicsScore,
            chemistry -> chemistryScore,
            biology -> biologyScore)

        (snumber,sname,map)
      }
    }

    val studentScoreDS: Dataset[(Int, String, String, Int)] = scoreTupleDS.map {
      case (snumber: Int, sname: String, map: mutable.Map[String, Int]) => {
        val iter: mutable.Iterable[(Int, String, String, Int)] = map.map {
          case (subject: String, score: Int) => {
            (snumber, sname, subject, score)
          }
        }
        iter.toList
      }
    }.flatMap(tuple => tuple)

    val studentScoreDF = studentScoreDS.toDF("snumber","sname","subject","score")

    import org.apache.spark.sql.functions._

    val studentTotalScoreDF = studentScoreDF.groupBy("snumber","sname").agg(sum("score").as("totalScore"))
      .withColumn("_rank",row_number().over(Window.orderBy(desc("totalScore"))))
      .selectExpr("snumber", "sname", "totalScore","_rank")

    //学生信息数据集
    val studentInfoDF = sinfoDF.map {
      case Row(line: String) => {
        val arr = line.split(" ")
        val snumber = arr(0)
        val sname = arr(1)
        val gender = arr(2)
        val age = arr(3)
        val sclass = arr(4)
        (snumber, sname, gender, age, sclass)
      }
    }.toDF("snumber", "sname", "gender", "age", "sclass")

    //学生成绩关联学生信息
    val resultDF01 = studentTotalScoreDF.join(studentInfoDF, Seq("snumber", "sname"), "left")
      .selectExpr("snumber", "sname", "totalScore", "_rank", "gender", "sclass")


    //老师信息数据集
    val teacherInfoDF = tinfoDF.map {
      rowLine => {
        val arr = rowLine.getString(0).split(" ")
        val tnumber = arr(0)
        val tname = arr(1)
        val subject = arr(2)
        val gender = arr(3)
        (tnumber, tname, subject, gender)
      }
    }.toDF("tnumber", "tname", "subject", "gender")


    //统计每个班级各学科最高分及老师信息
    val studentScoreJoinSinfoDF = studentScoreDF.join(studentInfoDF, studentScoreDF("snumber") === studentInfoDF("snumber") and (
      studentScoreDF("sname") === studentInfoDF("sname")), "left")
      .select(studentScoreDF("snumber"), studentScoreDF("sname"), studentScoreDF("subject"), studentScoreDF("score"),
        studentInfoDF("gender"), studentInfoDF("age"), studentInfoDF("sclass"))

    //这种方法输出字段只能是分组的key的元素，snumber、sname等字段不能使用
   // studentScoreJoinSinfoDF.groupBy("sclass","subject").agg(max("score") as("max_score"))
     // .selectExpr("sclass","subject","max_score")

    //通过开窗函数解决上述问题
    val studentScoreMaxDF = studentScoreJoinSinfoDF.withColumn("_rank", row_number()
      .over(Window.partitionBy("sclass", "subject")
        .orderBy(desc("score"))))
      .selectExpr("sclass", "subject", "score", "snumber", "sname", "gender", "age", "_rank")
      .where("_rank=1")
      .orderBy(asc("sclass"))
      .drop("_rank")

    val resultDF02 = studentScoreMaxDF.join(teacherInfoDF.toDF("tnumber", "tname", "subject", "tgender"), Seq("subject"),"left")
      .selectExpr("sclass", "subject", "score", "snumber", "sname", "gender", "age","tname","tgender")

    resultDF01.show()
    resultDF02.show()

  }
}
