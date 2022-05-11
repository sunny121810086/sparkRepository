package scalaDemo.collectionDemo.queue

import scala.collection.mutable.Queue

object QueueV1 {
  def main(args: Array[String]): Unit = {
    //创建
    val que01 = new Queue[Any]

    //添加元素
    que01 += "Lucy"
    que01 ++= List(1,2,3)
    println(que01) //Queue(Lucy, 1, 2, 3)

    que01 += List('A','B','C')
    println(que01) //Queue(Lucy, 1, 2, 3, List(A, B, C))

    //从队列的头部取出元素，队列que01本身也会变
    val elem = que01.dequeue()
    println(s"elem==$elem") //elem==Lucy
    println(que01) //Queue(1, 2, 3, List(A, B, C))

    //默认从尾部入队列，队列que01本身也会变
    que01.enqueue(3.14,6.28,9.32)
    println(que01) //Queue(1, 2, 3, List(A, B, C), 3.14, 6.28, 9.32)

    //返回队列元素，队列que02本身不变
    val que02 = new Queue[Any]
    que02 ++= List('A','B','C','D')
    //获取队列的第一个元素
    println(s"head：${que02.head}") //head：A
    //获取队列的最后一个元素
    println(s"last：${que02.last}") //last：D
    //获取队尾元素，即返回除了第一个以外剩余的元素
    println(s"tail：${que02.tail}") //tail：Queue(B, C, D)
    println(s"tail：${que02.tail.tail}") //tail：Queue(C, D)
  }
}
