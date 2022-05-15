package scalaDemo

object ConstractParam {
  def main(args: Array[String]): Unit = {
    val s = new Student2("sunny", "male", 28, 172)
    println(s"${s.name}\n${s.gender}\n${s.age}")
  }
}

//1 主构造器的形参未加修饰符，这个参数为局部变量
//2 val修饰，这个参数会作为类的私有属性，只能读取
//3 var修饰，这个参数会作为类的私有属性，既能读取又能赋值
class Student2(inName: String, val inGender: String, var inAge: Int, inHeight: Double) {
  val name: String = inName
  val gender: String = inGender
  var age: Int = inAge
  var height: Double = _
}