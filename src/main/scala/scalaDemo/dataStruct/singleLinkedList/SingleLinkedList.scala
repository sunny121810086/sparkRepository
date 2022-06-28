package scalaDemo.dataStruct.singleLinkedList

class SingleLinkedList {
  val head = new Node(0, "")

  //添加元素
  def add(node: Node) = {
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

  //按顺序添加
  def addByOrder(node: Node): Unit = {
    var flag = true
    var temp = head
    while (flag) {
      if (temp.next == null) {
        temp.next = node
        flag = false
      } else if (node.sno < temp.next.sno) {
        node.next = temp.next
        temp.next = node
        flag = false
      } else if (node.sno == temp.next.sno) {
        println("元素相同")
        flag = false
      } else {
        temp = temp.next
      }
    }
  }

  //删除元素
  def delete(sno: Int): Unit = {
    var flag = true
    var temp = head
    while (flag) {
      if (temp.next == null) {
        println("待删除的元素不存在")
        flag = false
      } else if (sno == temp.next.sno) {
        temp.next = temp.next.next
        flag = false
      } else {
        temp = temp.next
      }
    }
  }

  //遍历元素
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
