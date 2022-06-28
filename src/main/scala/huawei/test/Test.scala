package huawei.test

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Test {
  def main(args: Array[String]): Unit = {
    val map: mutable.Map[Int, Int] = mutable.Map.empty
    map(1) = map.getOrElse(1,0) + 9
    println(map.toBuffer)
    println('a'.toInt) //97
    println('z'.toInt) //122
    println('A'.toInt) //65
    println('Z'.toInt) //90
    val listbuff: ListBuffer[String] = mutable.ListBuffer.empty
    val arrbuff: ArrayBuffer[String] = mutable.ArrayBuffer.empty
    val queue: mutable.Queue[String] = mutable.Queue.empty
    queue.enqueue("a")
    queue.enqueue("b")
    queue.enqueue("c")
    queue.enqueue("d")
    val head = queue.head
    val last = queue.last
    val tail = queue.tail
    val elem = queue.dequeue()
    println(s"head==$head\nlast==$last\ntail==$tail\nelem==$elem\nqueue==$queue")

    val str = "$bo*y gi!r##l"
    val arr2 = elem.replaceAll("[^a-zA-Z]",",").split(",").filter(_.nonEmpty)
    println(arr2.reverse.mkString(" "))

    val tmpArr = new mutable.ArrayBuffer[String]()
    tmpArr += "aaa"
    println(tmpArr)

//    val list = List("A10","S10","W10","D10","D101","B10","10")
//    list.filter(_.matches("^[A|S|W|D]{1}[0-9]{1,2}$")).foreach(println(_))
    //val line = "A10;S20;W10;D30;X;A1A;B10A11;;A10;"
//    line.split(";").filter(_.matches("^[A|S|W|D]{1}[0-9]{1,2}$")).foreach(println(_))
//
//    line.split(";").filter(_.matches("^[ASWD]{1}[0-9]{1,2}$")).foreach(println(_))

    val charr = "0123456789".toCharArray
    charr.foreach(x => println(x.toInt))

  }
}
