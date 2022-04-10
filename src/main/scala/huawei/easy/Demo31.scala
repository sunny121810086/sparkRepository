package huawei.easy

import scala.io._


/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 编写一个程序，将输入字符串中的字符按如下规则排序。
  *
  * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
  *
  * 如，输入： Type 输出： epTy
  *
  * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
  *
  * 如，输入： BabA 输出： aABb
  *
  * 规则 3 ：非英文字母的其它字符保持原来的位置。
  *
  *
  * 如，输入： By?e 输出： Be?y
  *
  * 数据范围：输入的字符串长度满足 1 \le n \le 1000 \1≤n≤1000
  *
  * 输入描述：
  * 输入字符串
  * 输出描述：
  * 输出字符串
  * 示例1
  * 输入：
  * A Famous Saying: Much Ado About Nothing (2012/8).
  * 输出：
  * A aaAAbc dFgghh: iimM nNn oooos Sttuuuy (2012/8).
 * @Date: 2022/4/5
 * @Param null:
 * @return: null
 **/

object Demo31 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ( {line = StdIn.readLine(); line != null}) {
      val chArr = line.toCharArray
      val res = new Array[String](line.length)
      val sortArr = chArr.filter(_.toString.matches("[a-zA-Z]")).map(_.toUpper.toInt).toSet.toArray.sortWith(_<_)

      for(i <- 0 to line.length-1) {
        val ch = line.charAt(i)
        if(!ch.toString.matches("[a-zA-Z]")) {
          res(i) = ch.toString
        }
      }

      for(value <- sortArr) {
        for(el <- chArr) {
          if(el.toUpper.toInt ==value ) {
            for(j <- 0 to line.length-1) {
              if(res(j) != null) {
                res(j) = el.toString
              }
            }
          }
        }
      }

      for(x <- res) {
        print(x)
      }
      println()
    }
  }
}
