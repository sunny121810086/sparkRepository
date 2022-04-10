package huawei.easy

import scala.io._
import scala.collection.mutable._

object Demo35 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val buff = new ArrayBuffer[Int]()
      for(i <- 0 to line.length-1) {
        val ch = line.charAt(i)
        if(ch == '\"') {
          buff +=i
        }
      }

      var num = 1
      for(j <- 0 to line.length-1) {
        val ch2 = line.charAt(j)
        if(buff.contains(j)) {
          line.charAt(j)
        }
      }
    }
  }
}
