package scalaDemo.extendsDemo.demo03

object ScalaFieldOverrideDetail {
  def main(args: Array[String]): Unit = {
    val son = new Son06
  }
}

/**
  * 1.抽象字段(属性)：就是没有初始化的字段
  * 2.当一个类中含有抽象属性时，则该类需要标记为abstract
  * 3.对于抽象的属性，在底层不会生成对应的属性声明(成员变量)，而是生成两个对应的抽象方法(name()、name_$eq())
  **/
abstract class Father06 {
  var name: String  //抽象字段，可重写
  var age: Int = 99 //var非抽象属性，不能重写
}

/**
  * 说明：
  * 1.如果在子类中重写了父类的抽象属性，本质上是实现了抽象方法
  * 2.因此override也可以不写
  **/
class Son06 extends Father06 {
  override var name: String = "Lucy" //override可省略
  //override var age: Int = 18 //错误写法，因为var只能重写另一个抽象的var属性，否则运行会报错
}
