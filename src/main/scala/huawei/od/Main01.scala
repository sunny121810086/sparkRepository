package huawei.od

import scala.io.StdIn
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Main01 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    val arr: ArrayBuffer[Int] = mutable.ArrayBuffer.empty
    while ({line = StdIn.readLine(); line != null}) {
      val a = line.split(" ").map(_.toInt).filter(_ >= 0)
      val b = line.split(" ").map(_.toInt).filter(_ < 0)
      if (a.nonEmpty && b.nonEmpty) {
        a.foreach {
          x => {
            for (i <- 0 until b.length) {
              arr.append(x + b(i))
            }
          }
        }
        val res = arr.map(x => math.abs(x)).sortWith(_ < _).take(1)
        println(res(0))
        arr.clear()
      } else if (a.nonEmpty && b.isEmpty) {
        if (a.length >1) {
          println(a(0) + a(1))
        } else {
          println(a(0))
        }
      } else {
        if(b.length >1) {
          println(math.abs(b(b.length-1)+b(b.length-2)))
        } else {
          println(math.abs(b(0)))
        }
      }
    }
  }
}
