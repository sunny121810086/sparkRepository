package scalaDemo.extendsDemo.demo03

object TypeConvert {
  def main(args: Array[String]): Unit = {
    val s1 = new Son03
    val s2 = new Son033
    convert(s1)
    convert(s2)
  }

  //父类的引用可以接收所有子类的引用--多态
  def convert(fa: Father03) = {
    if (fa.isInstanceOf[Son03]) {
      val son = fa.asInstanceOf[Son03]
      son.sonMethod
    } else if (fa.isInstanceOf[Son033]) {
      val son = fa.asInstanceOf[Son033]
      son.sonMethod
    } else {
      println(s"类型转换失败==${fa.getClass}")
    }
  }
}

class Father03 {
  val name = "father"

  def baseInfo = {
    println(s"name is $name")
  }

  def fatherMethod = {
    println(s"method is $name")
  }
}

class Son03 extends Father03 {
  val son03Name = "son03"

  override def baseInfo: Unit = {
    println(s"sonName is $son03Name")
  }

  def sonMethod = {
    println(s"method is $son03Name")
  }
}

class Son033 extends Father03 {
  val son033Name = "son033"

  override def baseInfo: Unit = {
    println(s"sonName is $son033Name")
  }

  def sonMethod = {
    println(s"method is $son033Name")
  }
}