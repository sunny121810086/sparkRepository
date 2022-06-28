package huawei.test

import scala.io.StdIn

//简单密码
object Main13_2 {
  def main(args: Array[String]): Unit = {
    println(method(StdIn.readLine()))
  }

  def method(str: String) = {
    val map = Map(
      'a' -> 2, 'b' -> 2, 'c' -> 2,
      'd' -> 3, 'e' -> 3, 'f' -> 3,
      'g' -> 4, 'h' -> 4, 'i' -> 4,
      'j' -> 5, 'k' -> 5, 'l' -> 5,
      'm' -> 6, 'n' -> 6, 'o' -> 6,
      'p' -> 7, 'q' -> 7, 'r' -> 7, 's' -> 7,
      't' -> 8, 'u' -> 8, 'v' -> 8,
      'w' -> 9, 'x' -> 9, 'y' -> 9, 'z' -> 9
    )
    var ch = ' '
    var res = ""
    for (i <- 0 to str.length - 1) {
      ch = str.charAt(i)
      if (ch.toString.matches("[a-z]")) {
        res = res + map.getOrElse(ch, -1)
      } else if (ch.toString.matches("[A-Y]")) {
        res = res + (ch.toLower.toInt + 1).toChar
      } else if (ch == 'Z') {
        res = res + 'a'
      } else if (ch.toString.matches("[0-9]")) {
        res = res + ch
      } else {
        res = res + ch
      }
    }
    res
  }
}
