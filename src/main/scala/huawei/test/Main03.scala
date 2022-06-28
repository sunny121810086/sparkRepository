package huawei.test

import scala.io.StdIn

//字符串分隔
object Main03 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val sb = new StringBuffer()
    var res: String = null
    var num: Int = 0
    var mod: Int = 0
    while ({line = StdIn.readLine(); line != null}) {
      num = line.length / 8
      mod = line.length % 8
      if(mod == 0) {
        for (i <- 0 to num-1) {
          res = line.substring(i*8,(i+1)*8)
          println(res)
        }
      } else {
        //mod不为0且num=0时，即字符串总长<8，for (i <- 0 to -1) {}不会执行
        for (i <- 0 to num-1) {
          res = line.substring(i*8,(i+1)*8)
          println(res)
        }

        res = line.substring(num*8)
        sb.append(res)
        for (j <- 0 until(8 - res.length)) {
          sb.append("0")
        }

        println(sb.toString)
        sb.delete(0,sb.length())
      }
    }
  }
}
