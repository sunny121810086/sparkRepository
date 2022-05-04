package scalaDemo.implicitDemo.demo02

object ImplicitClassDemo01 {

  implicit class DataBaseFun01(val mysql: MySql) {
    def insert(): String = {
      s"insert..."
    }
  }

  def main(args: Array[String]): Unit = {
    val mysql = new MySql
    println(mysql.insert())
  }
}

class MySql {
  val dname: String = "mysql"

  def dbState = {
    s"mysql is running..."
  }

  /**
   * @Author: qwerdf@QAQ
   * @Description:
    * 总结：
    *     1.隐式类的构造参数有且只能有一个
    *     2.隐式类必须定义在类、伴生对象、包对象里面，不能是顶级的objects
    *     3.隐式类不能是case class
    *     4.作用域内不能有同名标识符，否则编译器无法识别
   * @Date: 2021/3/14
   * @Param null:
   * @return: null
   **/

}
