package huawei.easy

import scala.io._


/**
 * @Author: qwerdf@QAQ
 * @Description:  字符串最后一个单词的长度
 * @Date: 2022/2/21
 * @Param null:
 * @return: null
 **/

object Demo1 {
  def main(args :Array[String]): Unit = {
    try {
        val result = StdIn.readLine().split(" ")
        var len = result(result.length-1).length
        println(len)
    } catch {
      case e: Exception => {
        System.setOut(System.out)
        e.printStackTrace
      }
    }
  }
}
