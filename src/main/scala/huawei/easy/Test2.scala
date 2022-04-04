package huawei.easy

import scala.io._


object Test2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val arr= line.toCharArray
      for (x <- arr) {
        println(x.toInt)
      }
    }
  }
}
