package sparkCore

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object GroupAndTopN3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext

    val scoreRDD = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\studentScore.txt")
    val sinfoRDD = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\studentInfo.txt")
    val tinfoRDD = sc.textFile("F:\\ideaProjects\\spark-sql\\src\\main\\resources\\teacherInfo.txt")

    //分数
    val scoreTupleRDD: RDD[(Int, String, String, String, String, String, String, String)] = scoreRDD.map(line => {
      val arr = line.split(" ")
      val snumber = arr(0).toInt
      val sname = arr(1)
      val chineseScore = arr(2)
      val mathScore = arr(3)
      val englishScore = arr(4)
      val physicsScore = arr(5)
      val chemistryScore = arr(6)
      val biologyScore = arr(7)
      (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore)
    })

    //学生信息
    val sinfoTupleRDD = sinfoRDD.map {
      line => {
        val arr = line.split(" ")
        val snumber = arr(0).toInt
        val sname = arr(1)
        val sex = arr(2)
        val age = arr(3).toInt
        val className = arr(4).toInt
        (snumber, sname, sex, age, className)
      }
    }

    //老师信息
    val tinfoTupleRDD = tinfoRDD.map(line => {
      val arr = line.split(" ")
      val tnumber = arr(0).toInt
      val tname = arr(1)
      val subject = arr(2)
      val sex = arr(3)
      (tnumber, tname, subject, sex)
    })

    //需求一： 求出每个班级总分排名
    val snumberKeyRDD1: RDD[(Int, (Int, String, String, String, String, String, String, String))] = scoreTupleRDD.map {
      case (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore)
      => (snumber, (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore))
    }

    val snumberKeyRDD2 = sinfoTupleRDD.map {
      case (snumber, sname, sex, age, className)
      => (snumber, className)
    }

    val joinBySnumberRDD: RDD[(Int, ((Int, String, String, String, String, String, String, String), Int))] = snumberKeyRDD1.join(snumberKeyRDD2)

    val addClassNameRDD: RDD[(Int, String, String, String, String, String, String, String, Int)] = joinBySnumberRDD.map {
      case (snumber1, ((snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore), className))
      => (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore, className)
    }

    val groupByClassRDD: RDD[(Int, Iterable[(Int, String, String, String, String, String, String, String, Int)])] = addClassNameRDD.groupBy(_._9)

    val topNRDD: RDD[(Int, List[(Int, String, String, String, String, String, String, String, Int, Int)])] = groupByClassRDD.map {
      case (cname, iter) => {
        val addSumScoreList: List[(Int, String, String, String, String, String, String, String, Int, Int)] = iter.map {
          case (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore, className)
          => {
            val sumScore = chineseScore.split(":")(1).toInt +
                           mathScore.split(":")(1).toInt +
                           englishScore.split(":")(1).toInt +
                           physicsScore.split(":")(1).toInt +
                           chemistryScore.split(":")(1).toInt +
                           biologyScore.split(":")(1).toInt
            (snumber, sname, chineseScore, mathScore, englishScore, physicsScore, chemistryScore, biologyScore, sumScore, className)
          }
        }.toList

        val sortBySumScoreList = addSumScoreList.sortWith(_._9 > _._9)

        //val top2 = sortBySumScoreList.take(2)
        (cname, sortBySumScoreList)
      }
    }.sortByKey()

    println(topNRDD.collect().toBuffer)

    spark.stop()
  }
}
