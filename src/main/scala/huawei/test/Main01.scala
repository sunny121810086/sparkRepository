package huawei.test

import scala.io.StdIn

//字符串最后一个单词的长度
object Main01 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var arr: Array[String] = null
    while ({line = StdIn.readLine; line != null}) {
      arr = line.split(" ")
      println(arr(arr.length-1).length)
    }
  }
}
