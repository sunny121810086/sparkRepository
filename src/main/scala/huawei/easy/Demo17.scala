package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 单词倒排
  *              对字符串中的所有单词进行倒排。
  *              说明：
  *              1、构成单词的字符只有26个大写或小写英文字母；
  *              2、非构成单词的字符均视为单词间隔符；
  *              3、要求倒排后的单词间隔符以一个空格表示；如果原字符串中相邻单词间有多个间隔符时，倒排转换后也只允许出现一个空格间隔符；
  *              4、每个单词最长20个字母；
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo17 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val res1 = str.replaceAll("[^a-zA-Z]"," ")
    val res2 = res1.replaceAll("\\s{2,}"," ")
    method(res2)
  }
  def method(str: String): Unit = {
    val arr = str.split(" ")
    for(i <- 0 to arr.length-1 ) {
      print(arr(arr.length-1-i) + " ")
    }
  }
}
