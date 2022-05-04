package scalaDemo.extendsDemo.demo03

object OverrideMethod {
  def main(args: Array[String]): Unit = {
    val son02 = new Son02
    son02.getInfo
  }
}

class Father02 {
  var name: String = "Lucy"

  def getInfo = {
    println(s"Father==$name")
  }

  def sayHello = {
    println("hello~~~")
  }
}

class Son02 extends Father02 {
  override def getInfo: Unit = {
    println(s"Son==$name")
    //子类中调用父类被重写的方法，使用supper
    super.getInfo
    //如果方法没有被重写，在子类中直接调用
    sayHello
  }
}
