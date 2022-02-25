package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 提取不重复的整数
 * @Date: 2022/2/26
 * @Param null:
 * @return: null
 **/

object Demo16 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    var res = ""
    var ch: Char = '0'

    for (i <- 0 to str.length - 1) {
      ch = str.reverse.charAt(i)
      if (!res.contains(ch)) {
        res += ch
      }
    }
    println(res)
  }
}
