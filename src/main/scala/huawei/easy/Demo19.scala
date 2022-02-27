package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 统计大写字母个数
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo19 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](4)
    for(i <- 0 to 3) {
      arr(i) = StdIn.readLine()
    }

    var s = ""
    arr.foreach(str => {
      s = str.replaceAll("[A-Z]","")
      println(str.length - s.length)
    })
  }
}
