package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 验证尼科彻斯定理，即：任何一个整数m的立方都可以写成m个连续奇数之和。
  *
  * 例如：
  *
  * 1^3=1
  *
  * 2^3=3+5
  *
  * 3^3=7+9+11
  *
  * 4^3=13+15+17+19
  *
  * 输入一个正整数m（m≤100），将m的立方写成m个连续奇数之和的形式输出。
  * 数据范围：1\le m\le 100\1≤m≤100
  * 进阶：时间复杂度：O(m)\O(m) ，空间复杂度：O(1)\O(1)
  *
  * 输入描述：
  * 输入一个int整数
  *
  * 输出描述：
  * 输出分解后的string
  *
  * 示例1
  * 输入：
  * 6
  * 输出：
  * 31+33+35+37+39+41
 * @Date: 2022/4/5
 * @Param null:
 * @return: null
 **/

object Demo33 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val num = line.toInt
      val value = num*num*num
      var sum=0
      var flag = true
      var res = 1
      val arr = new Array[Int](num)
      for(i <- 1 to value if flag) {
        if(i%2 !=0) {
          for(j <- 1 to num){
            sum = sum + i+2*(j-1)
            arr(j-1) = i+2*(j-1)
          }
          if(sum==value) {
            flag = false
            res = i
          }
          sum =0
        }
      }
      println(arr.mkString("+"))
    }
  }
}
