package scalaDemo.collectionDemo.array

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object ArrayV3 {
  def main(args: Array[String]): Unit = {
    val arr01 = Array("Lucy","sunny")
    val arr02 = new ArrayBuffer[Any]()
    arr02.append("@Lucy","@sunny")

    //定长转变长数组
    //底层new mutable.ArrayBuffer[A1](size)
    val arr03: mutable.Buffer[Any] = arr01.toBuffer
    //添加多种类型元素
    arr03.append("@sunny",("@Lucy",18))
    arr03 += (3.14*2,3.14*3,("@Lucy",19))

    //变长转定长数组
    //底层new Array[B](size)
    val arr04: Array[Any] = arr02.toArray

    arr03.foreach(println(_))
    println("=========================")
    arr04.foreach(println(_))
  }
}
