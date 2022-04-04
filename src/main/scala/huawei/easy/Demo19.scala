package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 找出给定字符串中大写字符(即'A'-'Z')的个数。
  * 数据范围：字符串长度：1\le |s|\le 250\1≤∣s∣≤250
  * 字符串中可能包含空格或其他字符
  * 进阶：时间复杂度：O(n)\O(n) ，空间复杂度：O(n)\O(n)
  * 输入描述：
  * 对于每组样例，输入一行，代表待统计的字符串
  *
  * 输出描述：
  * 输出一个整数，代表字符串中大写字母的个数
  * 示例1
  * 输入：
  * A 1 0 1 1150175017(&^%&$vabovbaoadd 123#$%#%#O
  * 输出：
  * 2
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo19 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val replace = line.replaceAll("[A-Z]","")
      val dir = line.length - replace.length
      println(dir)
    }
  }
}
