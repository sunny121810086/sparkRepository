package huawei.test

import scala.io.StdIn
import util.control.Breaks._

//找出字符串中第一个只出现一次的字符
object Main24 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val map: Map[Char, Int] = line.toCharArray.map((_,1)).groupBy(_._1).mapValues(_.size).filter(_._2 ==1)
      breakable {
        line.toCharArray.foreach {
          x => {
            if (map.contains(x)) {
              println(x)
              break()
            }
          }
        }
        println("-1")
      }
    }
  }
}
