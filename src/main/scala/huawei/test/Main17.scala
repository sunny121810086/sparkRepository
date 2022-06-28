package huawei.test

import scala.io.StdIn

//单词倒排
object Main17 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var res = ""
    while ({line = StdIn.readLine(); line != null}) {
      res = line.replaceAll("[^a-zA-Z]",",")
        .split(",")
        .filter(_.nonEmpty)
        .reverse.mkString(" ")

      println(res)
    }
  }
}
