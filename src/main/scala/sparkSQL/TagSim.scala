package sparkSQL

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object TagSim {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
    val sc = spark.sparkContext

    val themeInfoRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\tagsim\\theme_info")
    val themeHeatInfoRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\tagsim\\theme_heat_info")
    val themeCandidateRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\tagsim\\theme_candidate_info")

    import spark.implicits._
    //获取主题资源数据集
    val themeInfoDF = themeInfoRDD.map {
      line => {
        val arr = line.split(" ")
        val theme_id = arr(0)
        val theme_ver = arr(1)
        val designer = arr(2)
        val tags = arr(3)
        (theme_id, theme_ver, designer, tags)
      }
    }.toDF("theme_id", "theme_ver", "designer", "tags")

    //获取主题资源候选数据集
    val themeCandidateDF = themeCandidateRDD.map {
      line => {
        val arr = line.split(" ")
        val rec_id = arr(0)
        val rec_ver = arr(1)
        val designer = arr(2)
        val tags = arr(3)
        (rec_id, rec_ver, designer, tags)
      }
    }.toDF("rec_id", "rec_ver", "designer", "tags")

    //获取主题资源热度数据集
    val themeHeatInfoDF = themeHeatInfoRDD.map {
      line => {
        val arr = line.split(" ")
        val theme_id = arr(0)
        val theme_ver = arr(1)
        val down_cnt = arr(2)
        (theme_id, theme_ver, down_cnt)
      }
    }.toDF("theme_id", "theme_ver", "down_cnt")

    //定义列的权重
    val colWeight = "designer:1,tags:2"

    val colWeightMap: Map[String, Double] = colWeight.split(",").map {
      x => {
        val arr = x.split(":")
        val colName = arr(0)
        val weight = arr(1).toDouble
        (colName,weight)
      }
    }.toMap

    import org.apache.spark.sql.functions._
    //将主题资源数据集和候选数据集按照版本关联，去掉同源资源
    val themeAndRecInfoDF = themeInfoDF.join(themeCandidateDF, themeInfoDF("theme_ver") === themeCandidateDF("rec_ver"), "left")
      .filter(themeInfoDF("theme_id") =!= themeCandidateDF("rec_id"))
      .select(themeInfoDF("theme_id"), themeInfoDF("theme_ver"), themeCandidateDF("rec_id"),themeCandidateDF("rec_ver"))

    themeAndRecInfoDF.show()

    var roundColWeightScoreDF = themeAndRecInfoDF.withColumn("score",lit(0.0)).select("theme_id","theme_ver","rec_id","score")

    for ((c,w) <- colWeightMap) {
      val roundColWeightDF = roundColWeight(themeInfoDF,themeCandidateDF,c)
      roundColWeightScoreDF = roundColWeightScore(roundColWeightScoreDF,roundColWeightDF,w,spark)
    }

    roundColWeightScoreDF.show()

    //考虑热度因素，进行最终得分计算
    val colWeightAndHeatScoreDF = colWeightAndHeatScore(roundColWeightScoreDF,themeHeatInfoDF)
    colWeightAndHeatScoreDF.show()
  }

  def colWeightAndHeatScore(roundColWeightScoreDF: DataFrame, themeHeatInfoDF: DataFrame) = {
    import org.apache.spark.sql.functions._
    roundColWeightScoreDF.join(themeHeatInfoDF.toDF("rec_id","theme_ver", "down_cnt"),Seq("rec_id","theme_ver"))
      .select("theme_id", "theme_ver", "rec_id", "score", "down_cnt")
      .withColumn("_rank",
        row_number().over(Window.partitionBy(col("theme_id"), col("theme_ver")).orderBy(desc("score"), desc("down_cnt"))))
      .withColumn("new_score", col("_rank")/10)
      .selectExpr("theme_id", "theme_ver", "rec_id", "score", "down_cnt", "1-new_score")
  }

  def roundColWeightScore(roundColWeightScoreDF: DataFrame,roundColWeightDF: DataFrame, w: Double,spark: SparkSession) = {
    import spark.implicits._
    import org.apache.spark.sql.functions._
    var rank = roundColWeightDF.map {
      case Row(theme_id: String, theme_ver: String, rec_id: String, theme_tag: String, rec_tag: String) => {
        val themeSet = theme_tag.split("\\|").toSet
        val recSet = rec_tag.split("\\|").toSet

        val score = w * themeSet.intersect(recSet).size / themeSet.union(recSet).size
        (theme_id, theme_ver, rec_id, score)
      }
    }.toDF("theme_id", "theme_ver", "rec_id", "score")

    rank = rank.join(roundColWeightScoreDF,Seq("theme_id","theme_ver","rec_id"))
        .select(rank("theme_id"),rank("theme_ver"),rank("rec_id"),rank("score"),roundColWeightScoreDF("score").as("tmp_score"))
        .withColumn("new_score",col("score")+col("tmp_score"))
        .select("theme_id","theme_ver","rec_id","new_score")
        .toDF("theme_id","theme_ver","rec_id","score")

    rank = rank.withColumn("_rank",
      row_number().over(Window.partitionBy(col("theme_id"),col("theme_ver")).orderBy(desc("score"))))
      .where(col("_rank")<=10)
      .drop("_rank")

    rank
  }

  def roundColWeight(themeInfoDF: DataFrame, themeCandidateDF: DataFrame, c: String) = {
    val themeDF = themeInfoDF.select("theme_id","theme_ver",c).toDF("theme_id","theme_ver","theme_tag")
    val recDF = themeCandidateDF.select("rec_id","rec_ver",c).toDF("rec_id","rec_ver","rec_tag")
    themeDF.join(recDF,themeDF("theme_ver")===recDF("rec_ver"),"left")
      .filter(themeDF("theme_id") =!= recDF("rec_id"))
      .select(themeDF("theme_id"),themeDF("theme_ver"),recDF("rec_id"),themeDF("theme_tag"),recDF("rec_tag"))
  }

}
