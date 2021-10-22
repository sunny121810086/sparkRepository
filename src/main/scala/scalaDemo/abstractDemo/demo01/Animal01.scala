package scalaDemo.abstractDemo.demo01

abstract class Animal01 {
  var name: String //抽象属性
  var sex: String
  val name2 = "Animal01" //普通属性

  def describ(): String  //抽象方法
  def action()  //抽象方法

  /**
   * @Author: qwerdf@QAQ
   * @Description:
    * 总结：
    * 抽象类不能被实例化，可以通过创建匿名子类对象来实现
    * 抽象类不一定有abstract方法，但是含有抽象方法或抽象属性的一定是抽象类
    * scala中抽象方法不能用abstract标记，定义方式：def temp()
    * 如果一个类继承了抽象类，则必须实现抽象类的所以抽象方法和抽象属性，除非它自己也声明为abstract类
    * 抽象方法和抽象属性不能使用private、final来修饰，因为这些关键字都是和重写/实现相违背的
    * 抽象类可以有实现的方法
    * 子类重写抽象方法不需要override，也可以加上
   * @Date: 2021/3/14
   * @Param null:
   * @return: null
   **/

}
