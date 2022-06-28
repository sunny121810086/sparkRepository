package huawei.test

import scala.io.StdIn

//句子逆序
object Main10 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val sb = new StringBuffer()
    while ({line = StdIn.readLine(); line != null}) {
      val arr = line.split(" ")
      for (i <- 0 until arr.length) {
        sb.append(arr(arr.length-1-i) + " ")
      }
      println(sb.toString)
      sb.delete(0, sb.length())
    }
  }
}
