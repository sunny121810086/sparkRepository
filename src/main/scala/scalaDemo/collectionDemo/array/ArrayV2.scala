package scalaDemo.collectionDemo.array

import scala.collection.mutable.ArrayBuffer

object ArrayV2 {
  def main(args: Array[String]): Unit = {
    //通过new的方式
    val arr01 = new ArrayBuffer[Any]()
    //添加元素
    arr01.append("Lucy")
    arr01 += "sunny"

    ////通过apply方法
    val arr02 = ArrayBuffer("Lucy","sunny",3.14)
    val arr03 = ArrayBuffer.apply("@Lucy","@sunny",3.14*2)

    //遍历元素
    //方式1
    for (elem <- arr01) {
      println(elem)
    }
    println("==========================")
    //方式2
    for (i <- 0 until arr02.length) {
      println(arr02(i))
    }
    println("==========================")
    //方式3
    arr03.foreach {
      x => println(x)
    }
  }
}
