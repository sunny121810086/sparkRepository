package scalaDemo.dataStruct.singleLinkedList

object SingleLinkedListTest {
  def main(args: Array[String]): Unit = {
    val lis = List(
      new Node(9,"Lucy"),
      new Node(5,"Tom"),
      new Node(2,"Alice"),
      new Node(8,"Sunny")
    )

    val sl = new SingleLinkedList
    lis.foreach(x => sl.addByOrder(x))
    sl.list

    println("**********************************")
    sl.addByOrder(new Node(4,"Jack"))
    sl.list

    println("**********************************")
    sl.delete(9)
    sl.list
  }
}
