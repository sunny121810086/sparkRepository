package scalaDemo.higherFunDemo

object HigherFunctionV1 {
  def main(args: Array[String]): Unit = {
    val res01 = hFuncA(funcA,3.14)
    val res02 = hFuncA(funcB,6.28)
    val res03 = hFuncA(funcC,9.42)
    val res04 = hFuncB(funcC,funcD,9.42)
    println(res01)
    println(res02)
    println(res03)
    println(res04)
  }

  val funcA: Double => Int = num => num.toInt*2

  def funcB = (num: Double) => num.toInt*2

  def funcC(num: Double): Int = {
    num.toInt*2
  }

  def funcD(num: Double) = {
    num*num
  }

  def hFuncA(f: Double => Int, num: Double): Int = {
    f(num)
  }

  def hFuncB(f1: Double => Int, f2: Double => Double, num: Double): Int = {
    f1(f2(num))
  }
}