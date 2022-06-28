package huawei.test

import scala.io.StdIn

object Main19 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var a = 0
    var sum  = 0
    var index = 0
    while ({line = StdIn.readLine(); line != null}) {
      line.split(",").foreach {
        x => {
          if (x == "1") {
            a += 1
          }

          if (x == "0" || index == line.split(",").length-1) {
            if (a >=3 ) {
              val mod = a - (a/3)*3
              if (mod > 0) {
                sum += (a/3 +1)
              } else {
                sum += a/3
              }
              a = 0
            } else if (a>0 && a<3) {
              sum += 1
              a = 0
            }
          }
          index +=1
        }
      }
      println(sum)
      sum = 0
      index = 0
    }
  }
}
