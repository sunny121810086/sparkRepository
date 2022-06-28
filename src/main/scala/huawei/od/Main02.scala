package huawei.od

import scala.io.StdIn

object Main02 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var sum = 0
    while ({line =StdIn.readLine(); line != null}) {
      val arr = line.replaceAll(",","").split("0").filter(_.length > 0)
      arr.foreach {
        str => {
          if (str.length % 3 == 0) {
            sum += str.length/3
          } else {
            val a = (str.length - (str.length % 3))/3
            sum += a+1
          }
        }
      }
      println(sum)
      sum = 0
    }
  }
}
