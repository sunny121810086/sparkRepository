package scalaDemo.implicitDemo.implicitFunction

object ImplicitFuncV2 {

  implicit def addDelete(mysql: MySql01) = {
    new Oper01
  }

  def main(args: Array[String]): Unit = {

//    implicit def addDelete(mysql: MySql01) = {
//      new Oper01
//    }
    val sql = new MySql01
    sql.insert(9)
    /**
     * 说明：
      * 1.当隐式函数定义在main方法内时: addDelete$1(sql).delete(10)
      * 2.当隐式函数定义在main方法外时: addDelete(sql).delete(10)
     **/

    sql.delete(10)
  }
}

class MySql01 {
  def insert(num: Int) = {
    println(s"插入数据：$num")
  }
}

class Oper01 {
  def delete(num: Int) = {
    println(s"删除数据: $num")
  }
}
