package scalaDemo.collectionDemo.list

import scala.collection.mutable.ListBuffer

object ListV2 {
  def main(args: Array[String]): Unit = {
    //创建
    val lis01 = new ListBuffer[Any] //创建一个空list
    val lis02 = ListBuffer[Any]("Lucy","sunny",3.14)
    val lis03 = ListBuffer("Lucy","sunny",3.14)

    println(lis01) //ListBuffer()
    println(lis02) //ListBuffer(Lucy, sunny, 3.14)
    println(lis03) //ListBuffer(Lucy, sunny, 3.14)

    //添加元素 +=、append、++=不会生成一个新得集合
    val lis04 = new ListBuffer[Any]() //可省略()
    lis04 += ("@Lucy","@sunny")
    lis04.append("A","B",18)
    println(lis04) //ListBuffer(@Lucy, @sunny, A, B, 18)

    val lis05 = ListBuffer(3.14,6.28)
    lis04 ++= lis05
    println(lis04) //ListBuffer(@Lucy, @sunny, A, B, 18, 3.14, 6.28)

    // ++、:+、+: 会生成一个新的集合
    val lis06 = ListBuffer("Lucy","@Lucy")
    val lis07 = ListBuffer("加菲猫","中华田园犬")
    val lis08 = lis06 ++ lis07
    val lis09 = lis08 :+ "1998"
    val lis10 = "1998" +: lis08
    println(lis06) //ListBuffer(Lucy, @Lucy)
    println(lis07) //ListBuffer(加菲猫, 中华田园犬)
    println(lis08) //ListBuffer(Lucy, @Lucy, 加菲猫, 中华田园犬)
    println(lis09) //ListBuffer(Lucy, @Lucy, 加菲猫, 中华田园犬, 1998)
    println(lis10) //ListBuffer(1998, Lucy, @Lucy, 加菲猫, 中华田园犬)

    //删除元素
    val elem = lis10.remove(0) //可以不用变量接收
    println(s"删除的元素：$elem") //删除的元素：1998
    println(lis10)  //ListBuffer(Lucy, @Lucy, 加菲猫, 中华田园犬)


  }
}
