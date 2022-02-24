package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 输入n个整数，输出其中最小的k个
  *              输入n个整数，输出其中最小的k个整数并按升序输出
 * @Date: 2022/2/25
 * @Param null:
 * @return: null
 **/

object Demo10 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[(String, String)](2)
    for (i <- 0 to 1) {
      arr(i) = (StdIn.readLine(), StdIn.readLine())
    }

    val resArr = method(arr)
    println(resArr(0))
    println(resArr(1))
  }

  def method(arr: Array[(String,String)]) ={
    var k = 0
    var i=0
    val res = new Array[String](2)
    for (tup <- arr) {
      k=tup._1.split(" ")(1).toInt
      res(i) = tup._2.split(" ").map(_.toInt).sortWith(_<_).take(k).mkString(" ")
      i=i+1
    }
    res

  }
}
