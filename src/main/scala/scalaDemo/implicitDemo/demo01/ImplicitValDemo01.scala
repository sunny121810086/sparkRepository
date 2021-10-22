package scalaDemo.implicitDemo.demo01

object ImplicitValDemo01 {

  implicit val name = "tom"
  implicit val age = 18
  def main(args: Array[String]): Unit = {
    implicitFun01
    implicitFun02("sunny")
   // implicitFun03//运行报错
  }

  def implicitFun01(implicit name: String = "jack") = {
    println(s"fun01:my name is $name")
  }

  def implicitFun02(implicit name: String = "jack") = {
    println(s"fun01:my name is $name")
  }

  def implicitFun03(implicit score: Double) = {
    println(s"fun01:my score is $score")
  }
  /**
   * @Author: qwerdf@QAQ
   * @Description:
    * 总结：
    *      1.隐式值优先级：传值 > 隐式值 > 默认值
    *      2.隐式值匹配时不能有二义性
    *      3.如果调用方法时，传值、隐式值、默认值都没有，程序会报错
   * @Date: 2021/3/14
   * @Param null:
   * @return: null
   **/

}
