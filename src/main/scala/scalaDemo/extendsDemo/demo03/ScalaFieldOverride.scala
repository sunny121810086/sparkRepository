package scalaDemo.extendsDemo.demo03

object ScalaFieldOverride {
  def main(args: Array[String]): Unit = {
    val fa: Father05 = new Son05
    val son: Son05 = new Son05
    println(fa.name)  //fa.name() --动态绑定机制，实际调用子类的name()
    println(son.name) //son.name()
  }
}

class Father05 {
  val name = "Lucy" //public String name(){}
}

class Son05 extends Father05 {
  override val name: String = "sunny" //public String name(){}
}