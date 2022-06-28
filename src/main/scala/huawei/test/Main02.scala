package huawei.test

import scala.io.StdIn

//计算某字符出现次数
object Main02 {
  def main(args: Array[String]): Unit = {
    val arr = new Array[String](2)
    var line: String = null
    var i = 0
    var numbers = 0
    while ({line = StdIn.readLine; line != null}) {
      arr(i) = line
      if (i == 1) {
        val charArr= arr(0).toCharArray
        charArr.foreach(x => {
          if (x.toString.toLowerCase == arr(1).toLowerCase) {
            numbers += 1
          }
        })
        println(numbers)
        i = 0
        numbers = 0
      } else {
        i +=1
      }
    }
  }
}
