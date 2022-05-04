package scalaDemo.implicitDemo.implicitFunction

object ImplicitFuncV1 {
  def main(args: Array[String]): Unit = {
    //底层编译成double2Int$1(double d)
    implicit def double2Int(d: Double) = {
      d.toInt
    }
    val num: Int = 3.14  //double2Int$1(3.14D)
  }
}

