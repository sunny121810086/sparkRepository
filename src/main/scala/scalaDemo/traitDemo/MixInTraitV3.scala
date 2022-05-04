package scalaDemo.traitDemo

object MixInTraitV3 {
  def main(args: Array[String]): Unit = {
    val sql07 = new MySql07 with Oper07 {
      override var oper_date: String = _
    }
  }
}

class MySql07 {

}

trait Oper07 {
  val num: Int = 66
  var oper_type: String = "insert"
  var oper_date: String  //抽象字段
  def insert() = {

  }
}
