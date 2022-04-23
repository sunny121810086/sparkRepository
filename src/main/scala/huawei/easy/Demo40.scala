package huawei.easy

import scala.io._
import scala.collection.mutable._

object Demo40 {
  def main(args: Array[String]): Unit = {
    var i = 0
    var j = 0
    var k = 0
    val arr = new ArrayBuffer[String]()
    var line: String = null
    while({line = StdIn.readLine(); line !=null}) {
      if(i ==0) {
        j = line.toInt
      }

      if(i >0 && k<j) {
        arr += line
        k = k+1
      }

      i = i+1

      if(k == j) {
        for(x <- arr) {
          println(x)
        }
        arr.clear()
        i=0
        k=0
      }
    }
  }
}
