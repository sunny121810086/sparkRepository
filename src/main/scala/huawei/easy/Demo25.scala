package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 找出字符串中第一个只出现一次的字符
 * @Date: 2022/3/3
 * @Param null:
 * @return: null
 **/

object Demo25 {
  def main(args: Array[String]): Unit = {
    val s1 = StdIn.readLine()
    val s2 = StdIn.readLine()
    val arr1 = s1.toCharArray
    val arr2 = s2.toCharArray
    println(method(arr1))
    println(method(arr2))
  }

  def method(arr: Array[Char]) = {
    var str: String = "-1"
    var num: Int = 0
    val map = arr.map((_, 1)).groupBy(_._1).map(x => (x._1, x._2.size))
    var flag = true
    for (i <- 0 to arr.length-1 if flag) {
      num = map.getOrElse(arr.charAt(i),' ')
      if (num == 1) {
        str = arr.charAt(i).toString
        flag = false
      }
    }
    str
  }
}
