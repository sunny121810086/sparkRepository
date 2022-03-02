package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: DNA序列
 * @Date: 2022/3/3
 * @Param null:
 * @return: null
 **/

object Demo26 {
  def main(args: Array[String]): Unit = {
    val s = StdIn.readLine()
    val n = StdIn.readLine().toInt
    println(method(s, n))
  }

  def method(str: String, n: Int) = {
    var per = 0.0
    var flag = true
    var ss = ""
    var res = ""
    for (i <- 0 to str.length - 1 if flag) {
      var num = 0
      var temp = 0.0
      ss = str.substring(i, i + n)
      for (j <- 0 to n - 1) {
        if (ss.charAt(j) == 'C' || ss.charAt(j) == 'G') {
          num += 1
        }
      }
      temp = num.toDouble / n
      if (temp > per) {
        res = ss
        per = temp
      }
      if (i + n == str.length) {
        flag = false
      }
    }
    res
  }
}
