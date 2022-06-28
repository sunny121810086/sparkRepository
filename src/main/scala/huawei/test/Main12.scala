package huawei.test

import scala.io.StdIn

//坐标移动
object Main12 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var m: Int = 0
    var n: Int = 0
    while ({line = StdIn.readLine(); line != null}) {
      //val arr = line.split(";").filter(_.matches("^[ASWD]{1}[0-9]{1,2}$"))
      val arr = line.split(";").filter(_.matches("^[A|S|W|D]{1}[0-9]{1,2}$"))
      arr.foreach {
        x => {
          val a = x.substring(0,1)
          val b = x.substring(1).toInt
          a match {
            case "A" => m = m - b
            case "D" => m = m + b
            case "W" => n = n + b
            case "S" => n = n - b
          }
        }
      }
      println(s"${m},${n}")
      m = 0
      n = 0
    }
  }
}
