package huawei.test

import scala.io.StdIn

object Main28 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val str = line.replaceAll("[A-Z]","")
      println(line.length - str.length)
    }
  }
}
