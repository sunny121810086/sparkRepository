package huawei.test

import scala.io.StdIn

object Main21_2 {
  def main(args: Array[String]): Unit = {
    val str1 = StdIn.readLine()
    val str2 = StdIn.readLine()
    method(str1)
    method(str2)
  }
  def method(str: String) = {
    val s1 = str.replaceAll("[a-zA-Z]+","")
    val s2 = str.replaceAll(" ","")
    val s3 = str.replaceAll("[0-9]+","")
    val len1 = str.length - s1.length
    val len2 = str.length - s2.length
    val len3 = str.length - s3.length
    val len4 = str.length - len1 - len2 - len3
    println(len1)
    println(len2)
    println(len3)
    println(len4)
  }
}
