package huawei.test

import scala.io.StdIn

//计算某字符出现次数
object Main02_2 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](2)
    var line: String = null
    var i = 0
    var numbers = 0
    var tempStr: String = null
    while ({line = StdIn.readLine; line != null}) {
      arr(i) = line
      if (i == 1) {
        tempStr = arr(0).toLowerCase.replaceAll(arr(1).toLowerCase(),"")
        numbers = arr(0).length - tempStr.length
        println(numbers)
        i = 0
      } else {
        i +=1
      }
    }
  }
}
