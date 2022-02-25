package huawei.easy

import scala.io._
import scala.collection._


/**
 * @Author: qwerdf@QAQ
 * @Description:  整型数组合并
  *               将两个整型数组按照升序合并，并且过滤掉重复数组元素
 * @Date: 2022/2/26
 * @Param null:
 * @return: null
 **/

object Demo12 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[(Int, String)](2)
    for (i <- 0 to 1) {
      arr(i) = (StdIn.readLine().toInt, StdIn.readLine())
    }

    val res = method(arr)
    res.foreach(print(_))
  }

  def method(arr: Array[(Int, String)]) = {
    val mset: mutable.Set[Int] = mutable.Set()
    for ((x, y) <- arr) {
      y.split(" ").foreach(elem => mset.add(elem.toInt))
    }
    mset.toList.sortWith(_<_)
  }

}
