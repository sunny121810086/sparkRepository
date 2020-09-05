package sparkStream

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object UpdateStateByKeyOperation {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(5))
    ssc.checkpoint("F:\\ideaProjects\\spark-version2\\src\\main\\resources\\checkPointDir")

    val dStream1: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.226.88",6666)
    val dStream2: DStream[String] = dStream1.flatMap(_.split(" "))
    val dStream3: DStream[(String, Int)] = dStream2.map((_,1))
    //当前批次统计(无状态)
    val dStream4: DStream[(String, Int)] = dStream3.reduceByKey((v1, v2) => v1 + v2)

    //历史批次统计(有状态)
    val updateStateByKeyDstream1 = dStream3.updateStateByKey(updateFunc1)

    /**
      * def updateStateByKey[S: ClassTag](
      * updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)],
      * partitioner: Partitioner,    //分区器
      * rememberPartitioner: Boolean  //true表示保存父RDD的信息
      * ): DStream[(K, S)]
      */
    val updateStateByKeyDstream2 = dStream3.updateStateByKey(updateFunc2,new HashPartitioner(sc.defaultMinPartitions),false)

    updateStateByKeyDstream1.print()

    ssc.start()
    ssc.awaitTermination()
  }

  /**
   * @Author: qwerdf@QAQ
   * @Description: updateFunc: (Seq[V], Option[S]) => Option[S]
    *           Seq[V] --> 当前批次中相同key的value序列,这里就是Seq(1,1,1...)
    *           Option[S] --> 历史批次相同key的value累计,初次buffer中没有记录，需要用Option类型封装
    *           函数返回Option[S] --> 当前和历史批次相同key累加的结果
   * @Date: 2020/8/9
   * @Param null:
   * @return: null
   **/
  val updateFunc1: (Seq[Int], Option[Int]) => Option[Int] = (seq,buffer) => {
    //当前批次相同单词次数
    val sumed = seq.sum
    //取出历史批次相同单词的累加次数,并和当前批次相加
    val buffered: Int = buffer.getOrElse(0) + sumed
    //封装Int类型
    Option(buffered)
  }


  /**
   * @Author: qwerdf@QAQ
   * @Description: updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
    *              K --> 相同key值
    *              Seq[V] --> 当前批次中相同key的value序列,这里就是Seq(1,1,1...)
    *              Option[S] --> 历史批次相同key的value累计,初次buffer中没有记录，需要用Option类型封装
    *              函数返回Iterator[(K, S)]
   * @Date: 2020/8/9
   * @Param null:
   * @return: null
   **/

  val updateFunc2: Iterator[(String, Seq[Int], Option[Long])] => Iterator[(String, Long)] = iter => {
    val res: Iterator[(String, Long)] = iter.map {
      tuple => {
        val word = tuple._1 //获取相同单词作为key值
        val sumed = tuple._2.sum.toLong
        val resultcount = tuple._3.getOrElse(0L) + sumed
        (word, resultcount)
      }
    }
    res
  }

  //一个入参时候,括号不能少
  val updateFunc2_1 = (iter: Iterator[(String, Seq[Int], Option[Long])]) => {

  }
}
