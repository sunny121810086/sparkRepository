package scalaDemo

object NullType {
  def main(args: Array[String]): Unit = {
    val cat = new Cat
    println(
      s"出生地：${cat.birthPlace}\n性别：${cat.gender}\n年龄：${cat.age}\n体重：${cat.height}\n是否可爱：${cat.isLovely}"
    )
  }
}

class Cat {
  var name: String = "狸花猫"
  var color: String = null
  var birthPlace = null     //Null类型,null为Null的一个实例
  var gender: String = _    //字符串类型默认值 null
  var age: Int = _    //Int类型默认值 0
  var height: Double = _  //Double类型默认值 0.0
  var isLovely: Boolean = _ //布尔类型默认值 false
}
