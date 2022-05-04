package scalaDemo.traitDemo

object MixInTraitV2 {
  def main(args: Array[String]): Unit = {
    /**
      * 说明：
      * 动态混入特质时，初始化顺序
      * 1.Oper06A...
      * 2.Oper06B...
      * 3.Oper06C1...
      * 4.Oper06C2...
      *
      * 调用delete方法执行顺序
      * 1.Oper06C2-删除数据：3.14
      * 2.Oper06C1-删除数据：3.14
      * 3.Oper06B-删除数据：3.14
      **/
    val sql06 = new MySql06 with Oper06C1 with Oper06C2
    println("======================================")
    sql06.delete(3.14)
  }
}

class MySql06 {

}

trait Oper06A {
  println("Oper06A...")

  def delete(num: Double)
}

trait Oper06B extends Oper06A { //特质,继承Oper06A
  println("Oper06B...")

  //实现或重写Oper06A中的抽象方法，override可省略
  override def delete(num: Double): Unit = {
    println(s"Oper06B-删除数据：$num")
  }
}

trait Oper06C1 extends Oper06B {
  println("Oper06C1...")

  //由于Oper06B中已经实现delete方法，这里只能是重写，override不能省略
  override def delete(num: Double): Unit = {
    println(s"Oper06C1-删除数据：$num")
    //动态混入时，这里不一定调用的是父类Oper06B中的方法
    //得看混入顺序--new MySql06 with Oper06C1 with Oper06C2
    super.delete(num)
  }
}

trait Oper06C2 extends Oper06B {
  println("Oper06C2...")

  override def delete(num: Double): Unit = {
    println(s"Oper06C2-删除数据：$num")
    //动态混入时，这里不一定调用的是父类Oper06B中的方法
    //得看混入顺序--new MySql06 with Oper06C1 with Oper06C2
    super.delete(num)
    //可以指定具体类型，从而访问到Oper06B中的delete方法
    //super[Oper06B].delete(num)
    //super[Oper06A].delete(num)  执行报错，因为Oper06A不是当前特质的直接父特质(超类)
  }
}