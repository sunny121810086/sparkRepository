package scalaDemo

import scala.collection.mutable

object MutableMap {
  def main(args: Array[String]): Unit = {

    val map1 = Map(("武林外史", "古龙"), ("绝代双骄", "古龙"), ("楚留香传奇", "古龙"), ("多情剑客无情剑", "古龙"))

    val mutMap = mutable.Map[String, String]()

    map1.foreach {
      case (x, y) => {
        if (x.length == 7)
          mutMap += ((x, y))
      }
    }

    //过滤器
    val filterMap: Map[String, String] = for {
      x <- map1
      if x._1.length == 7
    } yield x

    println(mutMap)
    println(filterMap)

  }
}
