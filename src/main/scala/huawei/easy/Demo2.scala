package huawei.easy

import scala.io._


/**
 * @Author: qwerdf@QAQ
 * @Description: 计算某字符出现次数
 * @Date: 2022/2/21
 * @Param null:
 * @return: null
 **/

object Demo2 {
  def main(args :Array[String]): Unit = {
    try {
      val str = StdIn.readLine().toLowerCase
      val ch = StdIn.readLine().toLowerCase
      val s = str.length-str.replaceAll(ch,"").length
      println(s)
    } catch {
      case e: Exception => {
        System.setOut(System.out)
        e.printStackTrace
      }
    }
  }
}
