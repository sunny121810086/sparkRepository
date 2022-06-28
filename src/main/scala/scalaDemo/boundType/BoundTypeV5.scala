package scalaDemo.boundType

object BoundTypeV5 {
  def main(args: Array[String]): Unit = {
    val list01 = List(new ElecGoodsA, new PhoneA)
    val list02 = List(new PhoneA, new HuaWeiPhoneA)
    //info01(list01) //报错 ElecGoodsA不是PhoneA类及其子类
    info01(list02)

    println("***************")


    /**
      * 下边界：A >: B  下界比较特殊，这里A类型其实可以传入任意类型
      *  传入和B有直系关系的，如果是B的父类还是按照父类处理，如果是B子类的按照B类型处理，
      *  传入和B没有继承和被继承关系的，一律按照Object处理
      **/
    val list03 = List(new ElecGoodsA, new PhoneA, new HuaWeiPhoneA)
    info02(list03).foreach(_.getInfo)

    //这里ElecGoodsB和PhoneA没有直系关系，调用info02方法后：
    //List[Object] = List(ElecGoodsB@3fb6a447)，所以asInstanceOf转换类型后才能访问getInfo方法
    val list04 = List(new ElecGoodsB)
    info02(list04).map(obj => obj.asInstanceOf[ElecGoodsB].getInfo)
    info02(list04).map(obj => println(obj.toString)) //scalaDemo.boundType.ElecGoodsB@3fb6a447

  }

  //上边界  A <: B 表示A为B类型或B类型的子类
  def info01[T <: PhoneA](list: List[T]) = list.foreach(_.getInfo)

  //下边界  A >: B  下界比较特殊，这里可以传入任意类型
  def info02[T >: PhoneA](list: List[T]) = list
}

class ElecGoodsB {
  private val message: String = "电子产品B"

  def getInfo = println(message)
}

class ElecGoodsA {
  private val message: String = "电子产品A"

  def getInfo = println(message)
}

class PhoneA extends ElecGoodsA {
  private val message: String = "手机"

  override def getInfo: Unit = println(message)
}

class HuaWeiPhoneA extends PhoneA {
  private val message: String = "华为手机"

  override def getInfo: Unit = println(message)
}