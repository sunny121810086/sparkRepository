package huawei.test

import scala.io.StdIn

//字符个数统计
object Main08 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val set = line.toCharArray.toSet
      println(set.size)
    }
  }
}
