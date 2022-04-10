package huawei.easy

import scala.io._

object Demo34 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val arr = new Array[String](5)
    var i = 0
    while({line = StdIn.readLine(); line !=null}) {
      if(i ==5) {
        println(arr(0))
        println(arr(1))
        println(arr(2))
        println(arr(3))
        println(arr(4))
        i=0
      }
      arr(i) = line
      i= i+1
    }
  }
}
