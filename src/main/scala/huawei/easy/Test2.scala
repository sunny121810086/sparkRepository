package huawei.easy

import scala.io._

object Test2 {
  def main(args: Array[String]): Unit = {
    var i = 0
    val arr = new Array[String](4)
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      if(i <=3) {
        arr(i) = line
        i = i+1
      }
      if(i==4) {
        for(x <- arr) {
          println(x)
        }
        i=0
      }
    }
  }
}
