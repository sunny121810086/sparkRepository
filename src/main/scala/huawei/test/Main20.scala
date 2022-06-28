package huawei.test

import scala.io.StdIn

//蛇形矩阵
object Main20 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var num = 0
    while ({line = StdIn.readLine(); line != null}) {
      num = line.toInt
      var a = 1
      var b = 0
      var tmp = ""
      for (i <- 0 until num) {
        a += i
        for (j <- 0 until (num - i)) {
          if (j == 0) {
            b = a
            tmp += s"$a "
          } else {
            b += i+j + 1
            tmp += s"$b "
          }
        }
        println(tmp.trim)
        tmp = ""
        b = 0
      }
      num = 0
    }
  }
}
