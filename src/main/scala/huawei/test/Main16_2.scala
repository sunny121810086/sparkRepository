package huawei.test

import scala.io.StdIn

object Main16_2 {
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
