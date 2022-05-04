package scalaDemo.traitDemo

object MixInTraitV1 {
  def main(args: Array[String]): Unit = {
/**
  * 说明：
  * 1.定义关键字trait。特质类似于java中的接口，java中实现接口关键字"implements"，scala中使用"with"
  *   如class A extends classB with traitC with traitD …
  **/
    val sql03 = new MySql03 with Oper03
    val sql04 = new MySql04 with Oper03
    //如果抽象类中有抽象方法，需要实现
    val sql05 = new MySql05 with Oper03 {
      override def insert(num: Double): Unit = {
        println(s"插入数据： $num")
      }
    }
    sql03.delete(3.14)
    sql04.delete(6.28)
    sql05.insert(9.42)
  }
}

trait Oper03 {
  def delete(num: Double) = {
    println(s"删除数据： $num")
  }
}

class MySql03 {

}

abstract class MySql04 {

}

abstract class MySql05 {
  def insert(num: Double)
}
