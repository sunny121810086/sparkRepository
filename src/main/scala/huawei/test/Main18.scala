package huawei.test

import scala.io.StdIn

//图片整理
object Main18 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      val arr1 = line.toCharArray.filter(_.toString.matches("[0-9]"))
      val arr2 = line.toCharArray.filter(_.toString.matches("[a-zA-Z]"))
      val res1 = arr1.sortWith(_.toInt < _.toInt).mkString("")
      val res2 = arr2.sortWith(_.toInt < _.toInt).mkString("")

      println(res1 + res2)
    }
  }
}
