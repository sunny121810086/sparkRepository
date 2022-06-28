package huawei.test

import scala.io.StdIn

object Main24_2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var flg = true
    while ({line = StdIn.readLine(); line != null}) {
      val map: Map[Char, Int] = line.toCharArray.map((_,1)).groupBy(_._1).mapValues(_.size).filter(_._2 ==1)
        line.toCharArray.foreach {
          x => {
            if (map.contains(x) && flg) {
              println(x)
              flg = false
            }
          }
        }
      if (flg) {
        println("-1")
      }
      flg = true
    }
  }
}
