package huawei.test

import scala.io.StdIn

//截取字符串
object Main22 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var i = 0
    val arr = new Array[String](2)
    while ({line = StdIn.readLine(); line != null}) {
      if (i != 2) {
        arr(i) = line
        i += 1
      }

      if (i == 2) {
        val str = arr(0).substring(0,arr(1).toInt)
        println(str)
        i = 0
      }
    }
  }
}
