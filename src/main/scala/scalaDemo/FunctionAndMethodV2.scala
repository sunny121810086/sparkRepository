package scalaDemo

object FunctionAndMethodV2 {
  def main(args: Array[String]): Unit = {
    val arr = Array(1.0,2,3)
    val pi = math.Pi
    val sumFunc2: Array[Double] => Double = arr => arr.sum + pi //闭包

    val sum1 = sumFunc1(arr,pi)
    val sum2 = sumFunc2(arr)
    val sum3 = sumMethod1(arr,pi)
    println(s"sum1=$sum1\r\nsum2=$sum2\r\nsum3=$sum3")
  }

 val sumFunc1 = (arr: Array[Double], pi: Double) => arr.sum + pi

  def sumMethod1(arr: Array[Double], pi: Double): Double = {
    arr.sum + pi
  }

}
