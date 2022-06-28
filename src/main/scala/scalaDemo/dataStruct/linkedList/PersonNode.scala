package scalaDemo.dataStruct.linkedList

class PersonNode(inNo: Long, inName: String, inAge: Int) {
  var no: Long = inNo
  var name: String = inName
  var age: Int = inAge
  var next: PersonNode = _

  override def toString: String = s"[no=$no,name=$name,age=$age]"
}
