package huawei.test

import scala.io._
import scala.collection.mutable._

//字符串分隔
object Main03_2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while ( {line = StdIn.readLine(); line != null}) {
      val len = line.length
      val num = len/8
      val buffer = new ArrayBuffer[String]()
      var tmpStr = ""
      if(len % 8 ==0) {
        for (i <- 0 to num-1) {
          tmpStr = line.substring(i*8,(i+1)*8)
          buffer += tmpStr
          //println(s"余数0 num==$num tmpStr=$tmpStr")
        }

      } else {
        if(num !=0) {
          for (i <- 0 to num-1) {
            tmpStr = line.substring(i*8,(i+1)*8)
            buffer += tmpStr
            //println(s"余数非0_1 num==$num tmpStr=$tmpStr")
          }

          tmpStr = line.substring(num*8)
          for (j <- 0 to ((num+1)*8 - len)-1) {
            tmpStr += "0"
            // println(s"余数非0_2 num==$num tmpStr=$tmpStr")
          }
          buffer += tmpStr
        } else {
          tmpStr = line.substring(0)
          for (j <- 0 to 8-len-1) {
            tmpStr +="0"
            // println(s"余数非0_3 num==$num tmpStr=$tmpStr")
          }
          buffer += tmpStr
        }
      }

      for (buf <- buffer) {
        println(buf)
      }

    }
  }
}
