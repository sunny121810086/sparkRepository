package scalaDemo.partialFunDemo

object PartialFunctionV1 {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3,3.14, "Lucy")
    val res01 = list.filter(_.isInstanceOf[Int]).map(func01(_))
    val res02 = list.map(func02(_)).filter(_.isInstanceOf[Int])
    println(res01)
    println(res02)

    //偏函数 可以处理多种类型
    val func04 = new PartialFunction[Any, Int] {
      override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int] || x.isInstanceOf[Double]

      override def apply(v1: Any): Int = if(v1.isInstanceOf[Int]) v1.asInstanceOf[Int]*2 else v1.asInstanceOf[Double].toInt +10
    }

    //偏函数简化形式
    def func05: PartialFunction[Any, Int] = {
      case x: Int => x * 2 //这里case语句可以自动转化成偏函数
      case x: Double => x.toInt + 10
    }

    //如果使用偏函数，则不能使用map算子，应该使用collect(相当于map+filter组合使用)
    val res04 = list.collect(func04)
    val res05 = list.collect(func05)
    println(res04)
    println(res05)
  }

  def func01(x: Any): Int = {
    x.asInstanceOf[Int] * 2
  }

  //模式匹配
  def func02(x: Any) = {
    x match {
      case num: Int => num * 2
      case _ =>
    }
  }

  //偏函数
  def func03 = {
    val value = new PartialFunction[Any, Int] {
      override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int]

      override def apply(v1: Any): Int = v1.asInstanceOf[Int] * 2
    }
    value
  }
}