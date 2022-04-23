package scalaDemo

import com.sun.xml.internal.bind.v2.TODO

object TryCatch {
  def main(args: Array[String]): Unit = {

    /**
      * @Author: qwerdf@QAQ
      * @Description: 将可能发生异常的代码封装在try块中，catch块用来捕获异常并处理，避免程序异常中止
      *              1.在scala中只有一个catch，在java中可以有多个
      *              2.在catch中有多个case，每个case匹配一种异常，即模式匹配思想。
      * @Date: 2022/4/24
      * @Param args:
      * @return: void
      **/
    try {
      val num = 9 / 0
    } catch {
      case ex: ArithmeticException => {
        //TODO  捕获异常，处理业务逻辑
        println("捕获了除数为零的算术异常")
      }
      case ex: Exception => {
        //TODO  捕获异常，处理业务逻辑
        println("捕获了异常")
      }
    } finally {
      //TODO 有无异常都会执行的代码块，一般用于关闭数据库连接、IO流等
      println("执行finally代码块...")
    }

    println("程序继续执行...")
  }
}
