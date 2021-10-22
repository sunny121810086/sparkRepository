package scalaDemo.extendsDemo.demo02

class Manager extends Employee {
  var bonus: Double = _

  def manage = {
    s"${name}正在开会ing..."
  }

  override def getAnnual: Double = {
    12*salary + bonus
  }
}
