package huawei.test

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

object Main15_2 {
  def main(args: Array[String]): Unit = {
    val i = StdIn.readLine().split(" ")
    val r = StdIn.readLine().split(" ")

    val arr_r = method2(r).toSet.toArray.sortWith(_.toInt < _.toInt)
    val arr_i = method2(i)

    val result = method(arr_i, arr_r)
    var m = result.size * 2
    var ss = ""

    for (n <- 0 to result.length - 1) {
      val v1 = result(n)._1
      val v2 = result(n)._2
      ss += v1 + " " + v2 + " "
      m = m + v2 * 2
      for (c <- result(n)._3) {
        ss += c._1 + " " + c._2 + " "
      }
    }
    println(m + " " + ss)
  }

  def method(arr_i: Array[String], arr_r: Array[String]) = {
    val res = new ArrayBuffer[(String, Int, Array[(Int, String)])]()
    var temp_r = ""
    for (i <- 0 to arr_r.length - 1) {
      val buffer = new ArrayBuffer[(Int, String)]()
      temp_r = arr_r(i)
      var temp_i = ""
      for (j <- 0 to arr_i.length - 1) {
        temp_i = arr_i(j)
        if (temp_i.contains(temp_r)) {
          buffer.append((j, temp_i))
        }
      }
      if (buffer.toArray.size > 0) {
        res.append((temp_r, buffer.toArray.size, buffer.toArray))
      }
    }
    res.toArray
  }

  def method2(arr: Array[String]): Array[String] = {
    val array = new Array[String](arr.length - 1)
    for (i <- 1 to arr.length - 1) {
      array(i - 1) = arr(i)
    }
    array
  }
}

