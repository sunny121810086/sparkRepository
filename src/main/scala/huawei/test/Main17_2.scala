package huawei.test

import scala.io._

object Main17_2 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val res1 = str.replaceAll("[^a-zA-Z]"," ")
    val res2 = res1.replaceAll("\\s{2,}"," ")
    method(res2)
  }
  def method(str: String): Unit = {
    val arr = str.split(" ")
    for(i <- 0 to arr.length-1 ) {
      print(arr(arr.length-1-i) + " ")
    }
  }
}
