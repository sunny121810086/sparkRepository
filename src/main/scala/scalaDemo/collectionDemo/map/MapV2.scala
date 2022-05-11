package scalaDemo.collectionDemo.map

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

object MapV2 {
  def main(args: Array[String]): Unit = {
    //创建可变Map
    val map01 = Map("杨幂" -> "三生三世十里桃花", "胡歌" -> "琅琊榜")
    val map02 = Map(("Lucy", 25), ("sunny", 31))

    //空集合--Map()
    val map03 = new HashMap[String, Int]

    //取值
    //方式1：map(key)
    println(map02("Lucy")) //key不存在会抛异常

    //方式2：contains(key)先判断key是否存在
    if (map02.contains("@sunny")) {
      map02("@sunny")
    }

    //方式3：get(key)
    val some01 = map02.get("Lucy")
    val some02 = map02.get("@Lucy")
    println(some01) //Some(25)
    println(some02) //None
    println(some01.get) //25
    //println(some02.get) //None类型不能再get

    //方式4：getOrElse(key,默认值)
    val value01 = map02.getOrElse("Lucy~~", "-1")
    println(value01) //-1

    //修改和添加
    val map04 = Map("杨幂" -> "三生三世十里桃花", "胡歌" -> "琅琊榜")
    map04("胡歌") = "仙剑奇侠传" //key存在，更新value值
    map04("鞠婧祎") = "叹云夕" //key不存在，相当于添加一个key-value
    map04.update("杨幂", "古剑奇谭") //update更新value值
    println(map04) //Map(鞠婧祎 -> 叹云夕, 胡歌 -> 仙剑奇侠传, 杨幂 -> 古剑奇谭)
    // += 也能添加元素，如果key存在，则更新
    map04 += "赵露思" -> "女"
    map04 += ("杨紫" -> "女", "任嘉伦" -> "男")
    map04 += (("Lucy", "女"), ("@Lucy", "女"))
    println(map04) //Map(任嘉伦 -> 男, 鞠婧祎 -> 叹云夕, 胡歌 -> 仙剑奇侠传, 赵露思 -> 女, 杨紫 -> 女, 杨幂 -> 古剑奇谭, @Lucy -> 女, Lucy -> 女)

    //删除--如果key不存在，也不会报错
    map04 -= ("Lucy", "@Lucy")
    println(map04) //Map(任嘉伦 -> 男, 鞠婧祎 -> 叹云夕, 胡歌 -> 仙剑奇侠传, 赵露思 -> 女, 杨紫 -> 女, 杨幂 -> 古剑奇谭)

    //遍历
    for ((k,v) <- map04) println(s"key==$k value==$v")

    for (k <- map04.keys) println(s"key==$k")

    for (v <- map04.values) println(s"value==$v")

    for (tuple <- map04) println(s"key==${tuple._1} value==${tuple._2}")
  }
}
