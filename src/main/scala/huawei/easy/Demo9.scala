package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 截取字符串
  *              输入一个字符串和一个整数 k ，截取字符串的前k个字符并输出
 * @Date: 2022/2/25
 * @Param null:
 * @return: null
 **/

object Demo9 {
  def main(args: Array[String]): Unit = {

    val arr = new Array[(String, Int)](2)
    for (i <- 0 to 1) {
      arr(i) = (StdIn.readLine(), StdIn.readLine().toInt)
    }

    val res = method(arr)
    for (c <- res) {
      println(c)
    }
  }

  def method(arr: Array[(String, Int)]) = {
    arr.map(
      x => {
        x._1.substring(0, x._2)
      }
    )

  }
}
