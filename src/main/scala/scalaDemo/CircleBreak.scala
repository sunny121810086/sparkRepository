package scalaDemo

import util.control.Breaks._

object CircleBreak {
  def main(args: Array[String]): Unit = {

    //方式1：通过调用break函数实现中断
    var num1 = 0
    breakable {
      while (num1 <= 50) {
        if (num1 % 2 != 0) {
          println(s"num1==$num1")
        }

        if (num1 == 9) {
          println("while循环中断...\r\nfor循环开始...")
          break()
        }
        num1 += 1
      }
    }

    breakable {
      for (num2 <- 1 to 50) {
        if (num2 % 2 == 0) {
          println(s"num2==$num2")
        }
        if (num2 == 10) {
          println("for循环中断...\r\n")
          break()
        }
      }
    }

    //方式2：通过循环守卫实现中断
    var flag = true
    //def apply(start: Int, end: Int, step: Int): Range = new Range(start, end, step)
    for (num3 <- Range(1, 10, 2) if flag) {
      if (num3 == 5) {
        flag = false
        println("结束循环...")
      } else {
        println(s"num3==$num3")
      }
    }

    //方式3：通过引入外部变量
    val arr = Array('a','b','c','d','e','f')
    var i = 0
    var elem = ' '
    var tmpf = true
    while ({elem = arr(i); i <= arr.length && tmpf}) {
      if (elem != 'd') {
        println(elem)
      } else {
        println("结束循环...")
        tmpf = false
      }

      i +=1
    }

  }
}
