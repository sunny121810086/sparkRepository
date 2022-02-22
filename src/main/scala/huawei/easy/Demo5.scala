package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 句子逆序
 * @Date: 2022/2/23
 * @Param null:
 * @return: null
 **/

object Demo5 {
  def main(args: Array[String]): Unit = {
    val line = StdIn.readLine()
    val arr = line.split(" ")

    var str = ""
    for (i <- 0 to arr.length-1) {
      str += arr(arr.length-1-i)
      str += " "
    }
    println(str)
  }
}
