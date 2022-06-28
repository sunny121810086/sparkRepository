package scalaDemo.dataStruct.singleLinkedList

class SingleLinkedList {
  val head = new Node(0,"")

  def add(node: Node)= {
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

  def list = {
    if (head.next == null) {
      println("链表为空")
    }
    else {
      var temp = head.next
      var flag = true

      while (flag) {
        if (temp != null) {
          println(temp)
          temp = temp.next
        } else {
          flag = false
        }
      }
    }
  }
}
