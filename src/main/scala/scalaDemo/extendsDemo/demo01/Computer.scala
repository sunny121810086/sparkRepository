package scalaDemo.extendsDemo.demo01

class Computer {
  var cpu: String = _
  var  memory: String = _
  var disk: String = _

  def getDetails(): String = {
    s"cpu: $cpu\nmemory: $memory\ndisk: $disk"
  }
}
