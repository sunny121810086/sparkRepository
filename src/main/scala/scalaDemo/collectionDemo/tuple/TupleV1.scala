package scalaDemo.collectionDemo.tuple

object TupleV1 {
  def main(args: Array[String]): Unit = {
    //创建
    //tuple01是一个Tuple，类型是Tuple4
    val tuple01: (String, String, Int, Int) = ("Lucy","sunny",18,19)

    //访问元素
    //根据顺序号--第一个元素
    println(tuple01._1)

    //根据索引--第一个元素
    /**
      * 底层实现：scala.Product4中的方法，含有Product1~Product22
      * 具体根据元组的类型去调用哪个Product中的方法，这里为scala.Product4
      * override def productElement(n: Int) = n match {
      * case 0 => _1
      * case 1 => _2
      * case 2 => _3
      * case 3 => _4
      * case _ => throw new IndexOutOfBoundsException(n.toString())
      * }
     **/
    println(tuple01.productElement(0))

    //遍历元组
    val tuple02 = ("@Lucy","@sunny",3.14)
    val iterator: Iterator[Any] = tuple02.productIterator
    for (item <- iterator) {
      println(item)
    }
  }
}
