package scalaDemo.boundType

object BoundTypeV1 {
  def main(args: Array[String]): Unit = {

    val s1 = new DoubleMessage[Double](3.141592658)
    val s2 = new CharMessage[Char]('A')
    println(s1.getInfo)
    println(s2.getInfo)
  }
}

//给类添加泛型
abstract class MessageA[T](s: T) {
  def getInfo = s
}

//子类继承抽象类时，限定范围
class DoubleMessage[Double](val s1: Double) extends MessageA(s1)

class CharMessage[Char](val s2: Char) extends MessageA(s2)