package huawei.test

import scala.io.StdIn

//数字颠倒
object Main09 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ({line = StdIn.readLine(); line != null}) {
      println(line.reverse)
    }
  }
}
