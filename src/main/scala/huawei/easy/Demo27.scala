package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 坐标移动
 * @Date: 2022/3/4
 * @Param null:
 * @return: null
 **/

object Demo27 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val tup = method(str)
    println(tup._1 + "," + tup._2)
  }

  def method(s: String) = {
    val arr = s.split(";")
    var x = 0
    var y = 0
    for (c <- arr) {
      var temp = ""
      var num = 0
      if (c.matches("^[ADWS][0-9]{1,2}$")) {
        temp = c.substring(0, 1)
        num = c.substring(1).toInt
        if (temp == "A") {
          x = x - num
        } else if (temp == "D") {
          x = x + num
        } else if (temp == "W") {
          y = y + num
        } else if (temp == "S") {
          y = y - num
        }
      }
    }
    (x, y)
  }
}
