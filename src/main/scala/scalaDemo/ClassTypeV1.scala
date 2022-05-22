package scalaDemo

object ClassTypeV1 {
  def main(args: Array[String]): Unit = {
    val a1 = new Father07A
    val a2 = new Child07A
    val a3 = new GrandSonA
    val a4 = new GrandSonSonA
    val a5 = new Father07B

    getInfo1(a4) //当前类型：class scalaDemo.GrandSonSonA
    getInfo1(a5) //当前类型：class scalaDemo.Father07B
  }

  def getInfo1[T >: GrandSonA](a: T) = {
    println(s"当前类型：${a.getClass}")
  }
}

class Father07B {}

class Father07A {}

class Child07A extends Father07A {}

class GrandSonA extends Child07A {}

class GrandSonSonA extends GrandSonA {}