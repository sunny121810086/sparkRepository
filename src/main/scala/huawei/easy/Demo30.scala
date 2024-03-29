package huawei.easy

import scala.io._


/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 输入一个只包含小写英文字母和数字的字符串，按照不同字符统计个数由多到少输出统计结果，如果统计的个数相同，则按照ASCII码由小到大排序输出。
  * 数据范围：字符串长度满足 1 \le len(str) \le 1000 \1≤len(str)≤1000
  *
  * 输入描述：
  * 一个只包含小写英文字母和数字的字符串。
  *
  * 输出描述：
  * 一个字符串，为不同字母出现次数的降序表示。若出现次数相同，则按ASCII码的升序输出。
  * 示例1
  * 输入：
  * aaddccdc
  * 输出：
  * cda
  * 说明：
  * 样例里，c和d出现3次，a出现2次，但c的ASCII码比d小，所以先输出c，再输出d，最后输出a.
 * @Date: 2022/4/4
 * @Param null:
 * @return: null
 **/


object Demo30 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val arr = str.toCharArray
    val map2: Map[Char, Int] = arr.map((_, 1)).groupBy(_._1).map(x => (x._1, x._2.size))

    val map3: Map[Int, Array[(Int, Char)]] = map2.toArray.map(x => (x._2, x._1)).groupBy(_._1)
    val sorted1: Array[(Int, Array[Char])] = map3.map {
      x => {
        val num = x._1
        val sortArr: Array[Char] = x._2.sortBy(_._2.toInt).map(_._2)
        (num, sortArr)
      }
    }.toArray

    val sorted2 = sorted1.sortBy(_._1).reverse

    var res = ""
    for ((rank, arr) <- sorted2) {
      for (ch <- arr) {
        res += ch.toString
      }
    }

    println(res)

  }
}
