package huawei.test

import scala.io.StdIn

import scala.collection.mutable

//合并表记录
object Main06 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var flag: Boolean = true
    var num: Int = 0
    var i = 0
    val map: mutable.Map[Int, Int] = mutable.Map.empty
    while ({line = StdIn.readLine(); line != null}) {
      if (flag) {
        num = line.toInt
        flag = false
      } else {
          val index = line.split(" ")(0).toInt
          val value = line.split(" ")(1).toInt
//          if (map.contains(index)) {
//            map(index) = map.getOrElse(index, 0) + value
//          } else {
//            map(index) = value
//          }
        map(index) = map.getOrElse(index, 0) + value
        i += 1
        if (i == num) {
          map.toList.sortBy(_._1).foreach {
            case (index,value) => println(s"$index $value")
          }
          map.clear()
          flag = true
          i = 0
        }
      }
    }
  }
}
