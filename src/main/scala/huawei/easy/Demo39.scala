package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 对输入的字符串进行加解密，并输出。
  *
  * 加密方法为：
  *
  * 当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；
  *
  * 当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
  *
  * 其他字符不做变化。
  *
  * 解密方法为加密的逆过程。
  * 数据范围：输入的两个字符串长度满足 1 \le n \le 1000 \1≤n≤1000  ，保证输入的字符串都是只由大小写字母或者数字组成
  * 输入描述：
  * 第一行输入一串要加密的密码
  * 第二行输入一串加过密的密码
  *
  * 输出描述：
  * 第一行输出加密后的字符
  * 第二行输出解密后的字符
  *
  * 示例1
  * 输入：
  * abcdefg
  * BCDEFGH
  * 输出：
  * BCDEFGH
  * abcdefg
 * @Date: 2022/4/13
 * @Param null:
 * @return: null
 **/

object Demo39 {
  def main(args: Array[String]): Unit = {
    var i = 0
    var arr = new Array[String](2)
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      if(i <=1) {
        arr(i) = line
        i = i+1
      }
      if(i == 2) {
        val res1 = encry(arr(0))
        val res2 = dencry(arr(1))
        println(res1)
        println(res2)
        i=0
      }
    }
  }

  //加密
  def encry(str: String): String = {
    var char =' '
    var res = ""
    for(i <- 0 to str.length-1) {
      char = str.charAt(i)
      if(char >='A' && char <'Z') {
        res = res + (char +1).toChar.toLower
      } else if(char >='a' && char <'z') {
        res = res + (char +1).toChar.toUpper
      } else if(char =='Z') {
        res = res + 'a'
      } else if(char =='z') {
        res = res + 'A'
      } else if(char >= '0' && char < '9') {
        res = res + (char +1).toChar
      } else if(char == '9') {
        res = res + '0'
      } else {
        res += char
      }
    }
    res
  }

  //解密
  def dencry(str: String): String = {
    var char =' '
    var res = ""
    for(i <- 0 to str.length-1) {
      char = str.charAt(i)
      if(char >'A' && char <='Z') {
        res = res + (char -1).toChar.toLower
      } else if(char >'a' && char <='z') {
        res = res + (char -1).toChar.toUpper
      } else if(char =='A') {
        res = res + 'z'
      } else if(char =='a') {
        res = res + 'Z'
      } else if(char > '0' && char <= '9') {
        res = res + (char -1).toChar
      } else if(char == '0') {
        res = res + '9'
      } else {
        res += char
      }
    }
    res
  }
}
