package huawei.test

import scala.io.StdIn

//统计字符
object Main21 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      var chCount = 0
      var spaceCount = 0
      var numCount = 0
      var otherCount = 0
      line.toCharArray.foreach {
        ch => {
          if (ch.toString.matches("[a-zA-Z]")) {
            chCount += 1
          } else if (ch.toString == " ") {
            spaceCount += 1
          } else if (ch.toString.matches("[0-9]")) {
            numCount += 1
          } else {
            otherCount += 1
          }
        }
      }
      println(chCount)
      println(spaceCount)
      println(numCount)
      println(otherCount)
    }
  }
}
