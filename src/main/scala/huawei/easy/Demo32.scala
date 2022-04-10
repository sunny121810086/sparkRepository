package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 任意一个偶数（大于2）都可以由2个素数组成，组成偶数的2个素数有很多种情况，本题目要求输出组成指定偶数的两个素数差值最小的素数对。
  *
  * 数据范围：输入的数据满足 4 \le n \le 1000 \4≤n≤1000
  * 输入描述：
  * 输入一个大于2的偶数
  *
  * 输出描述：
  * 从小到大输出两个素数
  * 示例1
  * 输入：
  * 20
  * 输出：
  * 7
  * 13
  * 示例2
  * 输入：
  * 4
  * 输出：
  * 2
  * 2
 * @Date: 2022/4/5
 * @Param null:
 * @return: null
 **/

object Demo32 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val num = line.toInt
      val mid = num / 2
      var mindv = num-2
      var tmp = 0
      var dv = 0
      var a = 1
      var b = num -1
      for(i <- 1 to mid) {
        tmp = num - i
        dv = tmp - i
        val f1 = method(i)
        val f2 = method(tmp)
        if(f1 && f2 && dv<mindv) {
          mindv = dv
          a = i
          b = tmp
        }
      }
      println(a)
      println(b)
    }

    def method(num: Int): Boolean = {
      var flag = true
      if(num > 2) {
        for(i <- 2 to num-1 if flag) {
          if(num % i ==0) {
            flag = false
          }
        }
      }
      flag
    }
  }
}
