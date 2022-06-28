package scalaDemo.dataStruct.singleLinkedList

class Node(no: Int, name: String) {
  var sno = no
  var sname = name
  var next: Node = _

  override def toString: String = s"[sno=$sno,sname=$sname]"
}
