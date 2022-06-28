package huawei.test

import scala.io.StdIn

//查找组成一个偶数最接近的两个素数
object Main25 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var flg = true
    while ({line = StdIn.readLine(); line != null}) {
      val a = line.toInt
      for (k <- a/2 until a; if flg) {
        if (method(k)) {
          if (method(a - k)) {
            println(s"${a-k}\n$k")
            flg = false
          }
        }
      }
      flg = true
    }
  }

  def method(num: Int): Boolean = {
    var i = 2
    var flag = true
    for (i <- 2 until num) {
      if (num % i == 0) {
        flag = false
      }
    }
    flag
  }
}
