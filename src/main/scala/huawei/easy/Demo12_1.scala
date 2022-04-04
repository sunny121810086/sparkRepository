package huawei.easy

import scala.io._
import scala.collection.mutable._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 题目标题：
  *
  * 将两个整型数组按照升序合并，并且过滤掉重复数组元素。
  * 输出时相邻两数之间没有空格。
  *
  *
  *
  * 输入描述：
  * 输入说明，按下列顺序输入：
  * 1 输入第一个数组的个数
  * 2 输入第一个数组的数值
  * 3 输入第二个数组的个数
  * 4 输入第二个数组的数值
  *
  * 输出描述：
  * 输出合并之后的数组
  * 示例1
  * 输入：
  * 3
  * 1 2 5
  * 4
  * -1 0 3 2
  * 输出：
  * -101235
 * @Date: 2022/4/4
 * @Param null:
 * @return: null
 **/

object Demo12_1 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var i = 1
    val mset: Set[Int] = Set()
    while ( {line = StdIn.readLine(); line != null}) {
      if (i % 2 == 0) {
        val split = line.split(" ").toSet
        for (el <- split) {
          mset.add(el.toInt)
        }
      }

      if (i % 4 == 0) {
        val res = mset.toArray.sortWith(_ < _)
        for (s <- res) {
          print(s)
        }
        println()
        mset.clear()
      }
      i = i + 1
    }

  }
}
