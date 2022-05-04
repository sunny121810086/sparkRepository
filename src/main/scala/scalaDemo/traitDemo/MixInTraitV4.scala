package scalaDemo.traitDemo

object MixInTraitV4 {
  def main(args: Array[String]): Unit = {
    /**
      * 说明：
      * 声明类时混入特质,即静态混入，初始化顺序
      * class MySql08B extends MySql08A with Oper08C1 with Oper08C2
      * 1.MySql08A...
      * 2.Oper08A...
      * 3.Oper08B...
      * 4.Oper08C1...
      * 5.Oper08C2...
      * 6.MySql08B...
      *
      * 动态混入特质，初始化顺序
      * new MySql08C with Oper08C1 with Oper08C2
      * 1.MySql08A...
      * 2.MySql08C...
      * 3.Oper08A...
      * 4.Oper08B...
      * 5.Oper08C1...
      * 6.Oper08C2...
      *
      * 调用delete方法执行顺序
      * 1.Oper08C2-删除数据：3.14
      * 2.Oper08C1-删除数据：3.14
      * 3.Oper08B-删除数据：3.14
      *
      * 1.Oper08C2-删除数据：6.28
      * 2.Oper08C1-删除数据：6.28
      * 3.Oper08B-删除数据：6.28
      **/
    val sql08b = new MySql08B
    println("===================================")
    //动态混入
    val sql08c = new MySql08C with Oper08C1 with Oper08C2
    println("===================================")

    //调用方法执行顺序
    sql08b.delete(3.14)
    println("===================================")
    sql08c.delete(6.28)
  }
}

class MySql08A {
  println("MySql08A...")
}

class MySql08B extends MySql08A with Oper08C1 with Oper08C2 {
  println("MySql08B...")
}

class MySql08C extends MySql08A {
  println("MySql08C...")
}

trait Oper08A {
  println("Oper08A...")

  def delete(num: Double)
}

trait Oper08B extends Oper08A {
  println("Oper08B...")

  override def delete(num: Double): Unit = {
    println(s"Oper08B-删除数据：$num")
  }
}

trait Oper08C1 extends Oper08B {
  println("Oper08C1...")

  override def delete(num: Double): Unit = {
    println(s"Oper08C1-删除数据：$num")
    super.delete(num)
  }
}

trait Oper08C2 extends Oper08B {
  println("Oper08C2...")

  override def delete(num: Double): Unit = {
    println(s"Oper08C2-删除数据：$num")
    super.delete(num)
  }
}