package huawei.test

import scala.io.StdIn

//进制转换
object Main04 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var sum: Long = 0L
    var arr: Array[Char] = null
    val map: Map[Char, Long] = Map(
      ('A', 10),
      ('B', 11),
      ('C', 12),
      ('D', 13),
      ('E', 14),
      ('F', 15)
    )
    while ({line = StdIn.readLine(); line != null}) {
      line = line.substring(2)
      arr = line.toCharArray
      for (i <- 0 until arr.length) {
        if(map.contains(arr(i))) {
          sum += map.getOrElse(arr(i), 0L) * math.pow(16, arr.length-1-i).toLong
        } else {
          sum += arr(i).toString.toLong * math.pow(16, arr.length-1-i).toLong
        }
      }
      println(sum)
      sum = 0
    }
  }
}
