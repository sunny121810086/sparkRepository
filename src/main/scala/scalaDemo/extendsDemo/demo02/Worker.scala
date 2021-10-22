package scalaDemo.extendsDemo.demo02

class Worker extends Employee {
  def work = {
    s"${name}正在幸苦的搬砖ing..."
  }

  override def getAnnual: Double = {
    12*salary
  }
}
