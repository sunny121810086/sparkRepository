package scalaDemo.chapter.chapter03

object MethodAndFunction {
  def main(args: Array[String]): Unit = {
    println("func: " + func1(1,9))

    m1(println("无参函数"))


  }

  val func1: (Int, Int) => Int = (x, y) => x + y

  val func2: (Int, Int) => Unit = (x, y) => println(x + y)


  def m1(f: => Unit) = {
    f
  }

}
