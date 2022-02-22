package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 字符串分隔
 * @Date: 2022/2/23
 * @Param null:
 * @return: null
 **/

object Demo3 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](2)

    for (i <- 0 to 1) {
      arr(i) = StdIn.readLine()
    }

    for (j <- 0 to arr.length - 1) {
      var str = arr(j)
      var tmp = ""

      while (str.length > 8) {
        tmp = str.substring(0, 8)
        println(tmp)
        str = str.substring(8)
      }

      while (str.length < 8) {
        str += "0"
      }

      println(str)
    }
  }
}
