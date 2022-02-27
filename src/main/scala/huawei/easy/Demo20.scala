package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 表示数字
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo20 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](2)
    for (i <- 0 to 1) {
      arr(i) = StdIn.readLine()
    }

    arr.foreach(str => {
      var res = ""
      var ch = ' '
      for (i <- 0 to str.length - 1) {
        ch = str.charAt(i)
        if (ch.toString.matches("[0-9]")) {
          res = res + "*" + ch + "*"
        } else {
          res += ch
        }
      }
      res = res.replaceAll("\\*{2,}", "")
      println(res)
    })
  }
}
