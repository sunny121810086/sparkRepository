package sparkSQL

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.mutable

object TagSimV2 {

  private val logger = Logger.getLogger(TagSimV2.getClass)

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
    val tagWeightInfoRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\tagsim\\tag_weight_info")
    val tagSimScoreInfoRDD = sc.textFile("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\tagsim\\tagsim_score_info")

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

    //获取列权重数据集
    val tagWeightInfoDF = tagWeightInfoRDD.map {
      line => {
        val arr = line.split(" ")
        val tag = arr(0)
        val weight = arr(1)
        (tag, weight.toDouble)
      }
    }.toDF("tag", "weight")

    //获取标签相似得分数据集
    val tagSimScoreInfoDF = tagSimScoreInfoRDD.map {
      line => {
        val arr = line.split(" ")
        val tag = arr(0)
        val simTag = arr(1)
        val simScore = arr(2)
        (tag, simTag, simScore.toDouble)
      }
    }.toDF("tag", "sim_tag", "sim_score")


    import org.apache.spark.sql.functions._
    //将主题资源数据集和候选数据集按照版本关联，去掉同源资源
    val themeAndRecInfoDF = themeInfoDF.join(themeCandidateDF, themeInfoDF("theme_ver") === themeCandidateDF("rec_ver"), "left")
      .filter(themeInfoDF("theme_id") =!= themeCandidateDF("rec_id"))
      .select(themeInfoDF("theme_id"), themeInfoDF("theme_ver"), themeCandidateDF("rec_id"), themeCandidateDF("rec_ver"))

    //获取主表标签
    val themeTagInfoDF = themeInfoDF.select("theme_id", "theme_ver", "tags")
    //获取标签权重
    val tagWeightMap = getTagWeightMap(tagWeightInfoDF)

    //获取标签相似得分
    val tagSimScoreMap = getTagSimScoreMap(tagSimScoreInfoDF)
    //初始化得分
    var roundColWeightScoreDF = themeAndRecInfoDF.withColumn("score", lit(0.0)).select("theme_id", "theme_ver", "rec_id", "score")

    roundColWeightScoreDF = roundColWeightScore(roundColWeightScoreDF, themeTagInfoDF, tagWeightMap, tagSimScoreMap)


    roundColWeightScoreDF.show()

    //考虑热度因素，进行最终得分计算
    val colWeightAndHeatScoreDF = colWeightAndHeatScore(roundColWeightScoreDF, themeHeatInfoDF)
    colWeightAndHeatScoreDF.show()

    Thread.sleep(Long.MaxValue)

    spark.stop()
  }

  def getTagSimScoreMap(tagSimScoreInfoDF: DataFrame) = {
    tagSimScoreInfoDF.rdd.map {
      case Row(tag: String, simTag: String, score: Double) => {
        val key = s"$tag-$simTag"
        (key, score)
      }
    }.collect().toMap
  }

  def getTagWeightMap(tagWeightInfoDF: DataFrame): Map[String, Double] = {
    tagWeightInfoDF.rdd.map {
      case Row(tag: String, weight: Double) => (tag, weight)
    }.collect().toMap
  }

  def getTagAndRecTagWeightMap(tagSet: Set[String], tagWeightMap: Map[String, Double]): mutable.Map[String, Double] = {
    import scala.collection.mutable
    val mmap: mutable.Map[String,Double] = mutable.Map[String,Double]()
    tagSet.foreach {
      tag => {
        val weight = tagWeightMap.getOrElse(tag, 0.0)
        mmap += ((tag, weight))
      }
    }
    mmap
  }

  def roundColWeightScore(roundColWeightScoreDF: DataFrame, themeInfoDF: DataFrame, tagWeightMap: Map[String, Double], tagSimScoreMap: Map[String, Double]) = {
    import scala.collection.mutable
    import org.apache.spark.sql.functions._
    import themeInfoDF.sparkSession.implicits._
    var rank = roundColWeightScoreDF.join(themeInfoDF.toDF("theme_id", "theme_ver", "theme_tag"), Seq("theme_id", "theme_ver"))
      .join(themeInfoDF.toDF("rec_id", "theme_ver", "rec_tag"), Seq("rec_id", "theme_ver"))
      .select("theme_id", "theme_ver", "rec_id", "theme_tag", "rec_tag", "score")
      .map {
        case Row(theme_id: String, theme_ver: String, rec_id: String, theme_tag: String, rec_tag: String, score: Double) => {
          val themeSet = theme_tag.split("\\|").toSet
          val recSet = rec_tag.split("\\|").toSet
          var sim = 0.0
          if (themeSet.nonEmpty && recSet.nonEmpty) {
            //获取主表标签权重
            val map1 = getTagAndRecTagWeightMap(themeSet, tagWeightMap)
            //获取候选表标签权重
            val map2 = getTagAndRecTagWeightMap(recSet, tagWeightMap)

            //两两组合主表和候选表标签，并将相似得分存储内存
            val listBF: mutable.ListBuffer[(String, String, Double)] = new mutable.ListBuffer[(String, String, Double)]
            for (tag1 <- themeSet) {
              for (tag2 <- recSet) {
                val key = s"$tag1-$tag2"
                val simScore = tagSimScoreMap.getOrElse(key, 0.0)
                listBF += ((tag1, tag2, simScore))
              }
            }

            val sortList = listBF.toList.sortBy(_._3).reverse

            var sumWeight: Double = 0.0
            var sumScore: Double = 0.0
            for(tuple <- sortList) {
              val key1 = tuple._1
              val key2 = tuple._2
              val score = tuple._3
              //主表标签权重
              val weight1 = map1.getOrElse(key1,0.0)
              //候选标签权重
              val weight2 = map2.getOrElse(key2,0.0)
              logger.error(s"key1:$key1 weight1:$weight1|key2:$key2 weight2:$weight2|score:$score")
              logger.error(s"sumWeight:$sumWeight sumScore:$sumScore")
              if(weight1 !=0.0 && weight2 !=0.0) {
                val minWeight = math.min(weight1,weight2)
                val tmpWeight1 = weight1 - minWeight
                val tmpWeight2 = weight2 - minWeight
                //更新主表和候选表相似标签的权重
                map1(key1) = tmpWeight1
                map2(key2) = tmpWeight2

                val tmpScore = minWeight*score
                sumWeight += minWeight
                sumScore += tmpScore
              }
            }
            sim = sumScore / sumWeight
          }
          (theme_id, theme_ver, rec_id, score + sim)
        }
      }.toDF("theme_id", "theme_ver", "rec_id", "score")

    rank = rank.withColumn("_rank",
      row_number().over(Window.partitionBy("theme_id", "theme_ver").orderBy(desc("score"))))
      .filter(col("_rank") <= 10)
      .drop("_rank")
      .select("theme_id", "theme_ver", "rec_id", "score")

    rank
  }

  def colWeightAndHeatScore(roundColWeightScoreDF: DataFrame, themeHeatInfoDF: DataFrame) = {
    import org.apache.spark.sql.functions._
    roundColWeightScoreDF.join(themeHeatInfoDF.toDF("rec_id", "theme_ver", "down_cnt"), Seq("rec_id", "theme_ver"))
      .select("theme_id", "theme_ver", "rec_id", "score", "down_cnt")
      .withColumn("_rank",
        row_number().over(Window.partitionBy(col("theme_id"), col("theme_ver")).orderBy(desc("score"), desc("down_cnt"))))
      .withColumn("new_score", col("_rank") / 10)
      .selectExpr("theme_id", "theme_ver", "rec_id", "score", "down_cnt", "1-new_score as score")
  }

}
