package huawei.easy

import scala.io._

/**
  * @Author: qwerdf@QAQ
  * @Description:
  * 描述
  * 找出字符串中第一个只出现一次的字符
  *
  *
  * 数据范围：输入的字符串长度满足 1 \le n \le 1000 \1≤n≤1000
  *
  *
  * 输入描述：
  * 输入一个非空字符串
  *
  * 输出描述：
  * 输出第一个只出现一次的字符，如果不存在输出-1
  * 示例1
  * 输入：
  * asdfasdfo
  *
  * 输出：
  * o
  * @Date: 2022/3/3
  * @Param null:
  * @return: null
  **/

object Demo25 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ( {
      line = StdIn.readLine(); line != null
    }) {
      val chArr = line.toCharArray
      var tmpS = ""
      val chMap: Map[Char, Int] = chArr.map((_, 1)).groupBy(_._1).map(x => (x._1, x._2.size)).filter(_._2 == 1)
      if (chMap.nonEmpty) {
        for ((k, v) <- chMap) {
          tmpS += k
        }
        var flag = true
        for (s <- chArr; if flag) {
          if (tmpS.contains(s.toString)) {
            flag = false
            println(s)
          }
        }
      } else {
        println("-1")
      }
    }
  }
}
