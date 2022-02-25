package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 字符个数统计
 * @Date: 2022/2/26
 * @Param null:
 * @return: null
 **/

object Demo15 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val num = str.toCharArray.toSet.size
    println(num)
  }
}
