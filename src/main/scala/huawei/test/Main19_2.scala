package huawei.test

import scala.io.StdIn

object Main19_2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var num = 0
    while ({line = StdIn.readLine(); line != null}) {
      val arr = line.replaceAll(",","").split("0").filter(_.length > 0)
      arr.foreach {
        x => {
          if (x.length % 3 == 0) {
            num += x.length/3
          } else {
            num += (x.length - x.length % 3)/3 + 1
          }
        }
      }
      println(num)
      num = 0
    }
  }
}
