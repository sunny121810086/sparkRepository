package scalaDemo.extendsDemo.demo01

object TestDemo01 {
  def main(args: Array[String]): Unit = {
    val notePad = new NotePad
    notePad.cpu = "core-i3"
    notePad.memory = "4GB"
    notePad.disk = "64GB"
    notePad.color = "white"
    println(notePad.getDetails())

    val pc = new PC
    pc.cpu = "core-i7"
    pc.memory = "16GB"
    pc.disk = "500TB"
    pc.brand = "lenovo-E480"
    println(pc.getDetails())

  }

}
