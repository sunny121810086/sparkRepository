package scalaDemo.extendsDemo.demo02

class Employee {
  var name: String = _
  var salary: Double = _

  def getAnnual: Double = {
    12*salary
  }
}
