package scalaDemo.higherFunDemo

object HigherFunctionV2 {
  def main(args: Array[String]): Unit = {
    val res01 = hFuncA(3)(3.14)
    println(res01)

    val func01 = hFuncA(4) // func01为3*y
    val res02 = func01(3.14)
    println(func01)  //<function1>
    println(res02)
  }

  //高阶函数也可以返回一个函数
  def hFuncA(x: Int) = {
    y: Double => x * y //匿名函数
  }
}