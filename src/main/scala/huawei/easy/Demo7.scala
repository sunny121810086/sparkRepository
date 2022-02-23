package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 字符串排序
  *              给定 n 个字符串，请对 n 个字符串按照字典序排列。
 * @Date: 2022/2/24
 * @Param null:
 * @return: null
 **/

object Demo7 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](10)
    for (i <- 0 to 9) {
      arr(i) = StdIn.readLine()
    }
     val res = arr.filter(x => x.matches("[a-zA-Z]+")).sortBy(x => x)

    for (c <- res) {
      println(c)
    }
  }
}
