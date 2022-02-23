package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 进制转换
  *              写出一个程序，接受一个十六进制的数，输出该数值的十进制表示
  *              输入：0xA
  *                    0xAA
  *              输出：10
  *                    170
 * @Date: 2022/2/23
 * @Param null:
 * @return: null
 **/

object Demo6 {
  def main(args: Array[String]): Unit = {
    val map = Map(
      '0' -> 0,
      '1' -> 1,
      '2' -> 2,
      '3' -> 3,
      '4' -> 4,
      '5' -> 5,
      '6' -> 6,
      '7' -> 7,
      '8' -> 8,
      '9' -> 9,
      'A' -> 10,
      'B' -> 11,
      'C' -> 12,
      'D' -> 13,
      'E' -> 14,
      'F' -> 15
    )
    val s1 = StdIn.readLine()
    val s2 = StdIn.readLine()

    val num1 = method(s1,map)
    val num2 = method(s2,map)
    println(num1)
    println(num2)
  }

  def method(str: String,map: Map[Char, Int]): Int ={
    var num=0
    for (i <- 0 to str.length - 1) {
      num +=map.getOrElse(str.charAt(i).toUpper,0)* Math.pow(16,str.length-1-i).toInt
    }
    num
  }
}
