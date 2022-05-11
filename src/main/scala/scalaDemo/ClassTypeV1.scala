package scalaDemo

object ClassTypeV1 {
  def main(args: Array[String]): Unit = {
    val a1 = new Father07A
    val a2 = new Child07A
    val a3 = new GrandSonA
    val a4 = new GrandSonSonA
    getInfo1[Child07A](a2)
  }

  def getInfo1[T >: GrandSonA](a: T) = {
    println(s"当前类型：${a.getClass}")
  }
}

class Father07A {}

class Child07A extends Father07A {}

class GrandSonA extends Child07A {}

class GrandSonSonA extends GrandSonA {}