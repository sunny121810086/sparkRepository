package scalaDemo.boundType

import scala.collection.immutable.Range

object BoundTypeV3 {
  def main(args: Array[String]): Unit = {
    val res = getMidIndex[Double](List(1.1,2.2,3.3,4.4,5.5))
    println(s"mix_value: $res")
  }

  //方法添加泛型
  def getMidIndex[T](list: List[T]) = {
    for(i <- Range(0, list.length)) {
      println(s"index=$i ${list(i)}")
    }
    list(list.length/2)
  }
}
