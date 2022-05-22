package scalaDemo.boundType

object BoundTypeV2 {
  def main(args: Array[String]): Unit = {
    val child = new Child08
    val father = new Father08
    val comm01: Comm01[Father08] = new Comm01[Child08](child)
    val comm02: Comm02[Child08] = new Comm02[Father08](father)

  }
}

class Father08

class Child08 extends Father08

//协变
class Comm01[+T](t: T)

//逆变
class Comm02[-T](t: T)
