package huawei.test

import scala.io.StdIn

//尼科彻斯定理
object Main26 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val num = line.toInt
      val target = math.pow(num,3)
      var flag = true
      var i = 1
      var sum = 0
      var str = ""
      while (flag) {
        for (j <- 1 to  num) {
          sum += i + 2*(j-1)
        }
        if (sum == target) {
          flag = false
        }
        sum = 0
        i += 2
      }

      for (k <- 1 to num) {
        str += s"${i-2 + 2*(k-1)}+"
      }
      println(str.substring(0,str.length-1))
    }
  }
}
