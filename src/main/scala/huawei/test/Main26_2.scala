
import scala.io._

object Main26_2 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    while({line = StdIn.readLine(); line != null}) {
      val num = line.toInt
      val value = num*num*num
      var sum=0
      var flag = true
      var res = 1
      val arr = new Array[Int](num)
      for(i <- 1 to value if flag) {
        if(i%2 !=0) {
          for(j <- 1 to num){
            sum = sum + i+2*(j-1)
            arr(j-1) = i+2*(j-1)
          }
          if(sum==value) {
            flag = false
            res = i
          }
          sum =0
        }
      }
      println(arr.mkString("+"))
    }
  }
}
