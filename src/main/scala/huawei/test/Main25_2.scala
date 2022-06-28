
import scala.io._

object Main25_2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val num = line.toInt
      val mid = num / 2
      var mindv = num-2
      var tmp = 0
      var dv = 0
      var a = 1
      var b = num -1
      for(i <- 1 to mid) {
        tmp = num - i
        dv = tmp - i
        val f1 = method(i)
        val f2 = method(tmp)
        if(f1 && f2 && dv<mindv) {
          mindv = dv
          a = i
          b = tmp
        }
      }
      println(a)
      println(b)
    }

    def method(num: Int): Boolean = {
      var flag = true
      if(num > 2) {
        for(i <- 2 to num-1 if flag) {
          if(num % i ==0) {
            flag = false
          }
        }
      }
      flag
    }
  }
}
