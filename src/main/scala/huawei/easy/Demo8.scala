package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description: 删除字符串中出现次数最少的字符
  *              输入：abcdd
  *                   aabcddd
  *              输出：dd
  *                   aaddd
 * @Date: 2022/2/24
 * @Param null:
 * @return: null
 **/

object Demo8 {
  def main(args: Array[String]): Unit = {
    val s1 = StdIn.readLine()
    val s2 = StdIn.readLine()

    val map1: Map[Char, Int] =  method(s1)
    val map2: Map[Char, Int] =  method(s2)

    val arr1 = map1.values.toArray.sortBy(x => x)
    val arr2 = map2.values.toArray.sortBy(x => x)

    val min_value1 = arr1(0)
    val min_value2 = arr2(0)

    val res1 = method2(min_value1,map1,s1)
    val res2 = method2(min_value2,map2,s2)

    println(res1)
    println(res2)
  }

  def method(str: String) = {
    val map: Map[Char, Int] = str.toCharArray.map((_,1)).groupBy(_._1).map(x => (x._1,x._2.size))
    map
  }

  def method2(min: Int, map: Map[Char, Int], str: String) = {
    var res: String = ""
    for (i <- 0 to str.length-1) {
      var key = str.charAt(i)
      var value = map.getOrElse(key,0)
      //println(s"key=$key value=$value")
      //println(s"res=$res")
      if (value != min) {
        res +=key
      }
    }
    res
  }
}
