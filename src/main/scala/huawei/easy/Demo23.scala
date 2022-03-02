package huawei.easy

import scala.io._

object Demo23 {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()
    val res: Array[(Char, Int, Int)] = str.toCharArray.map((_,1)).groupBy(_._1).map(x => (x._1,x._2.size,x._1.toInt)).toArray.sortWith(_._2 > _._2)
    var ss = ""
    for(i <- 0 to res.length-1) {
      val j = i +1
      if(j <= res.length-1) {
        val ch1 = res(i)._1
        val ch2 = res(j)._1
        val num1 = res(i)._2
        val num2 = res(j)._2
        val asc1 = res(i)._3
        val asc2 = res(j)._3
        if(num1 == num2 && asc1 < asc2) {
          ss = ss + ch1 + ch2
        }
      }
    }
  }
}
