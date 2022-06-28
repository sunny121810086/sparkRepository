package huawei.test

import scala.io.StdIn

object Main13 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val sb = new StringBuffer()
    val map = Map(
      ("Z","a"),
      ("1",1),("0",0),
      ("a",2),("b",2),("c",2),
      ("d",3),("e",3),("f",3),
      ("g",4),("h",4),("i",4),
      ("j",5),("k",5),("l",5),
      ("m",6),("n",6),("o",6),
      ("p",7),("q",7),("r",7),("s",7),
      ("t",8),("u",8),("v",8),
      ("w",9),("x",9),("y",9),("z",9)
    )
    while ({line = StdIn.readLine(); line != null}) {
      line.toCharArray.foreach {
        x => {
          if (map.contains(x.toString)) {
            sb.append(map.getOrElse(x.toString,""))
          } else if (x.toString.matches("^[A-Y]$")) {
            sb.append((x +1).toChar.toLower)
          } else {
            sb.append(x)
          }
        }
      }
      println(sb.toString)
      sb.delete(0, sb.length())
    }
  }
}
