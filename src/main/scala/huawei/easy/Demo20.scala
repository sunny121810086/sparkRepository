package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 将一个字符中所有的整数前后加上符号“*”，其他字符保持不变。连续的数字视为一个整数。
  *
  *
  * 数据范围：字符串长度满足 1 \le n \le 100 \1≤n≤100
  * 输入描述：
  * 输入一个字符串
  *
  * 输出描述：
  * 字符中所有出现的数字前后加上符号“*”，其他字符保持不变
  * 示例1
  * 输入：
  * Jkdi234klowe90a3
  * 输出：
  * Jkdi*234*klowe*90*a*3*
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo20 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      var tmpS = ""
      var res = ""
      for(i <- 0 to line.length-1) {
        tmpS = line.charAt(i).toString.replaceAll("\\*","#")
        if(tmpS.matches("[0-9]")) {
          res = res + "*" + tmpS + "*"
        }else {
          res +=tmpS
        }
      }
      res = res.replaceAll("\\*{2,}","").replaceAll("#","\\*")
      println(res)
    }
  }
}
