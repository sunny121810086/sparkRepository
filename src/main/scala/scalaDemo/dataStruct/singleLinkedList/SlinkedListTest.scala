package scalaDemo.dataStruct.singleLinkedList

object SlinkedListTest {
  def main(args: Array[String]): Unit = {
    val lis = List(
      new Node(1,"Lucy"),
      new Node(2,"Tom"),
      new Node(3,"Alice")
    )

    val sl = new SingleLinkedList
    val s2 = new SingleLinkedList
    lis.foreach(x => sl.add(x))
    sl.list
    s2.list
  }
}
