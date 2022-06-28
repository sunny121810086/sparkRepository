package scalaDemo.dataStruct.linkedList

class SingleLinkedList {
  val head: PersonNode = new PersonNode(0,"",0)

  //添加节点
  def add(node: PersonNode) = {
    var temp = head
    var flag = true
    while (flag) {
      if (temp.next == null) {
        temp.next = node
        flag = false
      } else {
        temp = temp.next
      }
    }
  }

  //遍历节点
  def allList= {
    if (head.next == null) {
      println("链表为空")
    } else {
      var temp = head.next
      var flag = true
      while (flag) {
        if (temp == null ) {
          flag = false
        } else {
          println(temp)
          temp = temp.next
        }
      }
    }
  }
}
