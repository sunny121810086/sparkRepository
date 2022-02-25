package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:  取近似值
 * @Date: 2022/2/26
 * @Param null:
 * @return: null
 **/

object Demo14 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val arr = str.split("\\.")
    var res = arr(0).toInt
    if(arr(1).substring(0,1).toInt >=5) {
      res = res +1
    }
    println(res)
  }
}
