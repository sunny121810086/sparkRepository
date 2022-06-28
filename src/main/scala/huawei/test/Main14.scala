package huawei.test

import scala.io.StdIn

//删除字符串中出现次数最少的字符
object Main14 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var min_value = 0
    val sb = new StringBuffer()
    while ({line = StdIn.readLine(); line != null}) {
//      val map: Map[Char, Int] = line.toCharArray.map((_, 1)).groupBy(_._1).map {
//        tup => {
//          (tup._1, tup._2.map(_._2).sum)
//        }
//      }
      val map: Map[Char, Int] = line.toCharArray.map((_, 1)).groupBy(_._1).map(x => (x._1,x._2.size))

      min_value = map.toArray.sortBy(_._2).take(1)(0)._2

      val keyList = map.filter(_._2 == min_value).keys.toList

      line.toCharArray.foreach {
        x => {
          if (!keyList.contains(x)) {
            sb.append(x)
          }
        }
      }
      println(sb)
      sb.delete(0, sb.length())
    }
  }
}
