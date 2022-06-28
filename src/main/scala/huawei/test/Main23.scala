package huawei.test

import scala.io.StdIn

//输入n个整数，输出其中最小的k个
object Main23 {
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
        val k = arr(0).split(" ")(1).toInt
        val sortArr = arr(1).split(" ").sortWith(_.toInt < _.toInt)
        println(sortArr.take(k).mkString(" "))
        i = 0
      }
    }
  }
}
