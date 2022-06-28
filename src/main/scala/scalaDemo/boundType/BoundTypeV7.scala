package scalaDemo.boundType

object BoundTypeV7 {
  def main(args: Array[String]): Unit = {
    val child = new Child08A
    val father = new Father08A
    child.info //Father...

    //编译阶段报错：Top08A[Father08A]和Top08A[Child08A]没有继承关系
    //val top01: Top08A[Father08A] = new Top08A[Child08A](child)

    //协变
    val top02: Top08B[Father08A] = new Top08B[Child08A](child)

    //逆变
    val top03: Top08C[Child08A] = new Top08C[Father08A](father)

  }
}

class Father08A {
  private val message = "Father..."
  val job = "work..."

  def info = println(message)
}

class Child08A extends Father08A {
  private val message = "Child..."
  override val job: String = "study..."
}

//不变
class Top08A[A](one: A) {
  def getClassInfo = {
    one.getClass.getName
  }
}

//协变
class Top08B[+A](one: A) {
  def getClassInfo = {
    one.getClass.getName
  }
}

class Top08C[-A](one: A) {
  def getClassInfo = {
    one.getClass.getName
  }
}