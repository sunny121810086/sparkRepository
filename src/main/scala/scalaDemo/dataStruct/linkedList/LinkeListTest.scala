package scalaDemo.dataStruct.linkedList

object LinkeListTest {
  def main(args: Array[String]): Unit = {
    val singList = new SingleLinkedList

    val list = List(
      new PersonNode(1,"Luccy",18),
      new PersonNode(2,"Sunny",28),
      new PersonNode(3,"Tom",19),
      new PersonNode(4,"Alice",22)
    )

    for (elem <- list) {
      singList.add(elem)
    }
    singList.allList

  }
}
