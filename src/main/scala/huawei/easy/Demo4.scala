package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 数字颠倒, 字符串反转
 * @Date: 2022/2/23
 * @Param null:
 * @return: null
 **/

object Demo4 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    var ch =""
    for (i <- 0 to str.length-1) {
      ch += str.charAt(str.length-i-1)
    }
    println(ch)
  }
}
