package scalaDemo.collectionDemo.list

object ListV1 {
  def main(args: Array[String]): Unit = {
    //创建
    val lis01 = List("Lucy","sunny",18,19)
    val lis02 = Nil //表示一个空集合List()
    println(lis01)
    println(lis02)

    //访问List元素
    val lis03 = List("@Lucy","@sunny",18,19,20)
    println(lis03(0)) //索引从0开始，取出第一个元素

    //追加元素--生成新List，原集合不变
    //方式1 :+
    val lis04 = List("A","B","C")
    val lis05 = lis04 :+ "Lucy"  //在列表最后追加元素
    //方式2 +:
    val lis06 = "sunny" +: lis04 //在列表前边追加元素
    println(lis04) //List(A, B, C)
    println(lis05) //List(A, B, C, Lucy)
    println(lis06) //List(sunny, A, B, C)

    //方式3 ::
    val lis07 = List("A","B","C")
    //向一个空列表添加元素
    val lis08 = 1::2::3::lis07::Nil
    println(lis08) //List(1, 2, 3, List(A, B, C))

    //方式4 :::  该符号左右必须是集合才能使用
    //将集合中的每一个元素添加到空集合
    val lis09 = 1::2::3::lis07:::Nil
    println(lis09) //List(1, 2, 3, A, B, C)

  }
}
