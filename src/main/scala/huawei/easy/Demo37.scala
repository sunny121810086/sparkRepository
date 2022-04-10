package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 蛇形矩阵是由1开始的自然数依次排列成的一个矩阵上三角形。
  *
  * 例如，当输入5时，应该输出的三角形为：
  * 1 3 6 10 15
  * 2 5 9 14
  * 4 8 13
  * 7 12
  * 11
  *
  * 输入描述：
  * 输入正整数N（N不大于100）
  *
  * 输出描述：
  * 输出一个N行的蛇形矩阵。
 * @Date: 2022/4/10
 * @Param null:
 * @return: null
 **/

object Demo37 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line !=null}) {
      var num = line.toInt
      var x = 0
      var y = 1
      for(i <- 1 to num) {
        y=y+i-1
        for(j <- 1 to num-i+1) {
          if(j==1) {
            x=y
          } else {
            x = x+(i-1)+j
          }
          print(x+" ")
        }
        println()
      }
    }
  }
}
