package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 回文字符串
 * @Date: 2022/2/28
 * @Param null:
 * @return: null
 **/

object Demo21 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val rev = str.reverse

    for(i <- 1 to rev.length){
      for(j <- 0 to (i-1)) {
        if(str.contains(rev.substring(j,rev.length-i+1+j))) {
          println(rev.length-i+1)
          return
        }
      }
    }
  }
}
