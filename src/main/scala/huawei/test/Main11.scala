package huawei.test

import scala.io.StdIn

//字符串排序
object Main11 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var flag: Boolean = true
    var num = 0
    var arr: Array[String] = null
    var i = 0
    while ({line = StdIn.readLine(); line != null}) {
      if (flag) {
        num = line.toInt
        arr = new Array[String](num)
        flag = false
      } else {
        arr(i) = line
        i += 1
        if (i == num) {
          val sortArr = arr.sortWith(_ < _)
          sortArr.foreach(println(_))
          flag = true
          i = 0
        }
      }
    }
  }
}
