package huawei.test

import scala.io.StdIn

//提取不重复的整数
object Main07 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val sb = new StringBuffer()
    while ({line = StdIn.readLine(); line != null}) {
      line.reverse.toCharArray.foreach {
        x => {
          if (!sb.toString.contains(x.toString)) {
            sb.append(x.toString)
          }
        }
      }
      println(sb.toString)
      sb.delete(0,sb.length())
    }
  }
}
