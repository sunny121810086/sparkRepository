package scalaDemo.extendsDemo.demo01

class NotePad extends Computer {
  var color: String = _

  override def getDetails(): String = {
    s"cpu: $cpu\nmemeor: $memory\ndisk: $disk\ncolor: $color"
  }
}
