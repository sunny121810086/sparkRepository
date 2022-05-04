package scalaDemo.implicitDemo.implicitClass

object ImplicitClassV1 {
  def main(args: Array[String]): Unit = {

    /***
      *说明：
      * implicit关键字声明一个class后，对应底层编译生成Oper02$1()方法，并返回隐式类ImplicitClassV1$Oper02$2
      * private final ImplicitClassV1$Oper02$2 Oper02$1(MySql02 mysql) {
      *   return new ImplicitClassV1$Oper02$2(mysql);
      * }
      *
      *总结：
      * 1.隐式类的构造参数有且只能有一个
      * 2.隐式类必须定义在类、伴生对象、包对象里面，不能是顶级的objects
      * 3.隐式类不能是case class
      * 4.作用域内不能有同名标识符，否则编译器无法识别
     **/
    implicit class Oper02(val mysql: MySql02) {
      def delete(sql: MySql02) = {
        println(s"delete num: ${sql.num}")
      }
    }

    val sql02 = new MySql02
    sql02.insert()
    sql02.delete(sql02) //Oper02$1(sql02).delete(sql02)

  }
//  implicit class Oper02(val mysql: MySql02) {
//    def delete(sql: MySql02) = {
//      println(s"delete num: ${sql.num}")
//    }
//  }
}

class MySql02 {
  val num = 3.14
  def insert(num2: Double = 6.28) = {
    println(s"insert num: $num2")
  }
}
