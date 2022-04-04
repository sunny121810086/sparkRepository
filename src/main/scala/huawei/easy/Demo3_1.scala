package huawei.easy

import scala.io._
import scala.collection.mutable._


/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * •输入一个字符串，请按长度为8拆分每个输入字符串并进行输出；
  *
  * •长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
  * 输入描述：
  * 连续输入字符串(每个字符串长度小于等于100)
  *
  * 输出描述：
  * 依次输出所有分割后的长度为8的新字符串
  *
  * 示例1
  * 输入：
  * abc
  * 输出：
  * abc00000
 * @Date: 2022/4/4
 * @Param null:
 * @return: null
 **/

object Demo3_1 {
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
