package huawei.test

import scala.io.StdIn

//字符串加解密
object Main16 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var i= 0
    val arr = new Array[String](2)
    var res1 = ""
    var res2 = ""
    while ({line = StdIn.readLine(); line != null}) {
      if (i != 2) {
        arr(i) = line
        i += 1
      }

      if (i == 2) {
        //加密
        arr(0).toCharArray.foreach {
          x => {
            if (x >= 'a' && x < 'z') {
              res1 = res1 + (x - 31).toChar
            } else if (x >= 'A' && x < 'Z') {
              res1 = res1 + (x + 33).toChar
            } else if (x == 'z') {
              res1 = res1 + 'A'
            } else if (x == 'Z') {
              res1 = res1 + 'a'
            } else if (x == '9') {
              res1 = res1 + 0
            } else {
              res1 = res1 + (x.toString.toInt + 1)
            }
          }
        }

        //解密
        arr(1).toCharArray.foreach {
          x => {
            if (x > 'a' && x <= 'z') {
              res2 = res2 + (x - 33).toChar
            } else if (x > 'A' && x <= 'Z') {
              res2 = res2 + (x + 31).toChar
            } else if (x == 'A') {
              res2 = res2 + 'z'
            } else if (x == 'a') {
              res2 = res2 + 'Z'
            } else if (x == '0') {
              res2 = res2 + 9
            } else {
              res2 = res2 + (x.toString.toInt - 1)
            }
          }
        }
        println(s"$res1 \n$res2")
        i = 0
        res1 = ""
        res2 = ""
      }
    }
  }
}
