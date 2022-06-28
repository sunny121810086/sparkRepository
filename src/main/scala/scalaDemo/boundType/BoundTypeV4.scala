package scalaDemo.boundType

object BoundTypeV4 {
  def main(args: Array[String]): Unit = {

    /**
      * 调用了Predef中的隐式函数：
      * implicit def int2Integer(x: Int)          = java.lang.Integer.valueOf(x)
      * implicit def double2Double(x: Double)     = java.lang.Double.valueOf(x)
      * 指定泛型后，入参中的Int和Double类型通过隐式转换成了实现Comparable接口的java中的Integer和Double类型
      **/
    val com01 = new Compare[Integer](Integer.valueOf(2), 5)
    val com02 = new Compare[java.lang.Double](6.6, 9.9)
    println(com01.myCompare)
    println(com02.myCompare)
  }
}

/**
  * 上边界：A <: B 表示A为B类型或B类型的子类
  * T <: Comparable[T] 这里表示T类型是实现了Comparable接口的一个类型
  * 这样传入的参数类型可以直接调用compareTo方法
  **/

class Compare[T <: Comparable[T]](one: T, other: T) {
  private val i: Int = one.compareTo(other)

  def myCompare = if (i > 0) one else other
}
