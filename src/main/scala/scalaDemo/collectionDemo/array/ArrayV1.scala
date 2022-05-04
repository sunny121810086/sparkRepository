package scalaDemo.collectionDemo.array

object ArrayV1 {
  def main(args: Array[String]): Unit = {
    //通过new的方式
    val arr01 = new Array[Any](3)
    arr01(0) = "Lucy"
    arr01(1) = "sunny"

    //通过apply方法
    val arr02 = Array("Lucy","sunny",3.14)
    val arr03 = Array.apply("@Lucy","@sunny",3.14*2)

    //直接循环出数组元素
    for (elem <- arr01) {
      println(elem)
    }
    println("===========================")
    //通过下标取值
    for (i <- 0 until arr02.length) {
      println(arr02(i))
    }
    println("===========================")
    //foreach方式
    arr03.foreach {
      x => println(x)
    }

  }
}
