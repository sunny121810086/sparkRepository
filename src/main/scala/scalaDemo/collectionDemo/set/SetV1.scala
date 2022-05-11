package scalaDemo.collectionDemo.set

import scala.collection.mutable
import scala.collection.mutable.HashSet

object SetV1 {
  def main(args: Array[String]): Unit = {
    //创建
    val set01 = mutable.Set.empty[Any]
    val set02 = mutable.Set("Lucy",1,2,3)
    val set03 = new HashSet[String] ()
    val set04 =  mutable.HashSet.empty[Any]

    //添加元素
    set01 += ("@Lucy","sunny",2008,2018)
    set04.add("@Lucy")
    println(set01) //Set(@Lucy, sunny,2008,2018)

    //删除元素
    set01 -= "sunny"
    set01.remove(2008)
    println(set01) //Set(@Lucy,2018)

    //遍历
    for (elem <- set01) println(elem)
    set01.foreach {elem => println(elem)}
  }
}
