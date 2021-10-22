package scalaDemo.extendsDemo.demo01

class PC extends Computer {
  var brand: String = _

  override def getDetails(): String = {
    s"cpu: $cpu\nmemeor: $memory\ndisk: $disk\nbrand: $brand"
  }
}
