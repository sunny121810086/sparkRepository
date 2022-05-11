package scalaDemo.collectionDemo.map

object MapV1 {
  def main(args: Array[String]): Unit = {

    /**
      * @inline def -> [B](y: B): Tuple2[A, B] = Tuple2(self, y)
      *         def →[B](y: B): Tuple2[A, B] = ->(y)
      **/
    //创建不可变Map--输出顺序和声明顺序一致
    val map01 = Map("鞠婧祎" -> 25, "刘亦菲" -> 31)
    val map02 = Map(("Lucy", 25), ("sunny", 31))
    println(map01) //Map(鞠婧祎 -> 25, 刘亦菲 -> 31)
    println(map02) //Map(Lucy -> 25, sunny -> 31)
  }
}
