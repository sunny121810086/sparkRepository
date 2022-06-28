package huawei.test

import scala.io.StdIn

object Main05 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var num: Int = 0
    while ({line = StdIn.readLine(); line != null}) {
      num = line.split("\\.")(1).substring(0,1).toInt
      if (num < 5) {
        println(line.toDouble.toInt)
      } else {
        println(line.toDouble.toInt + 1)
      }
    }
  }
}
