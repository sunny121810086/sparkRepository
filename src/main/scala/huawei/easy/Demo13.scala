package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:  字符串字符匹配
  *              输入两个字符串。第一个为短字符串，第二个为长字符串。两个字符串均由小写字母组成
  *              如果短字符串的所有字符均在长字符串中出现过，则输出字符串"true"。否则输出字符串"false"
 * @Date: 2022/2/26
 * @Param null:
 * @return: null
 **/

object Demo13 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[(String, String)](2)
    for (i <- 0 to 1) {
      arr(i) = (StdIn.readLine(), StdIn.readLine())
    }

    val booleans = method(arr)
    booleans.foreach(println(_))

  }

  def method(arr: Array[(String, String)]) = {
    val res = new Array[Boolean](arr.length)
    var flag: Boolean = true
    for (i <- 0 to arr.length - 1) {
      arr(i)._1.toCharArray.foreach(x => {
        if (!arr(i)._2.contains(x)) {
          flag = false
        }
      })
      res(i) = flag
      flag = true
    }
    res
  }
}
