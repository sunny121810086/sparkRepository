package huawei.easy

import scala.io._
import java.io._
import scala.collection.JavaConversions._

object Test {
  def main(args :Array[String]): Unit = {
    val outCapture = new ByteArrayOutputStream
    val stdout = System.out
    try {
      var line: String = null
      while ({line = StdIn.readLine(); line != null}) {
        val result = line.split(' ')
        var a = result(0).toInt
        var b = result(1).toInt
        println(a + b)
      }
    } catch {
      case e: Exception => {
        System.setOut(stdout)
        e.printStackTrace
      }
    }
  }
}
