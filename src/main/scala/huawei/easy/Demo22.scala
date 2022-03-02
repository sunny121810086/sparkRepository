package huawei.easy

import scala.io._
import scala.collection._

object Demo22 {
  def main(args: Array[String]): Unit = {
    val num = StdIn.readLine().toInt
    var min_temp = 0
    var max_temp = 0
    var res = 0;
    for(i <- 1 to num/2) {
      min_temp = num/2 - i +1
      max_temp = num - min_temp
      if(method(min_temp)== true && method(max_temp) == true) {
        max_temp - min_temp
      }
    }
    println(res)
  }

  def method(num: Int): Boolean = {
    var flag = false
    for(i <- 2 to num-1) {
      if(num % i !=0) {
        flag = true
      }
    }
    flag
  }
}
