package scalaDemo.extendsDemo.demo02

object TestDemo02 {
  def main(args: Array[String]): Unit = {
    val worker = new Worker
    worker.name = "张小三"
    worker.salary = 4000

    val manager = new Manager
    manager.name = "张大三"
    manager.salary = 10000
    manager.bonus = 30000

    println(showEmpAnnal(worker))
    println(showEmpAnnal(manager))
    println(testWokr(worker))
    println(testWokr(manager))
  }

  def showEmpAnnal(empl: Employee) = {
    if(empl != null) {
      empl.getAnnual
    } else {
      0.0
    }

  }

  def testWokr(empl: Employee) = {
    if(empl != null) {
      if(empl.isInstanceOf[Worker]) {
        val worker = empl.asInstanceOf[Worker]
        worker.work
      } else if (empl.isInstanceOf[Manager]) {
        val manager = empl.asInstanceOf[Manager]
        manager.manage
      } else {
        "其他类型"
      }
    } else {
      "实例为null"
    }
  }
}
