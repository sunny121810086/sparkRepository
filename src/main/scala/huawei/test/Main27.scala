package huawei.test

import scala.io.StdIn

//字符串字符匹配
object Main27 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val arr: Array[String] = new Array[String](2)
    var i = 0
    var flag = true
    while ({line = StdIn.readLine(); line != null}) {
      if (i != 2) {
        arr(i) = line
        i += 1
      }

      if (i == 2) {
        arr(0).toCharArray.foreach {
          x => {
            if (!arr(1).contains(x.toString)) {
              flag = false
            }
          }
        }
        println(flag)
        i = 0
        flag = true
      }
    }
  }
}
