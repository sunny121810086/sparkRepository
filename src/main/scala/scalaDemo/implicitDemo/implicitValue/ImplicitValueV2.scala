package scalaDemo.implicitDemo.implicitValue

object ImplicitValueV2 {
  def main(args: Array[String]): Unit = {
    implicit val name: String = "Lucy"
   // implicit val name2: String = "Lucy2"  不能有两个相同类型的隐式值，否则编译器会无法识别

    /***
     * 说明
      * 1.隐式值优先级：传值 > 隐式值 > 默认值
      * 2.隐式值匹配时不能有二义性
      * 3.如果调用方法时，传值、隐式值、默认值都没有，程序会报错
     **/
    showInfo1  //输出Lucy
    showInfo1("@sunny") //输出@sunny
    showInfo2 //输出18
    //showInfo3//不传值会报错
  }

  def showInfo1(implicit inName: String = "sunny")= {
    println(s"name is $inName")
  }

  def showInfo2(implicit inAge: Int = 18) = {
    println(s"age is $inAge")
  }

  def showInfo3(implicit inAge: Int) = {
    println(s"age is $inAge")
  }
}
