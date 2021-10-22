package scalaDemo.overrideDemo

class FatherA(lastName: String) {
  private var lover: String = _

  println(s"父类主构造器~~~~$lastName")

  def this(lastName: String, lover: String) {
    this(lastName)
    this.lover = lover
    println(s"父类辅助构造器~~~~")
  }

  def fatherMethod = {
    println(s"父类特有的方法：~~~~")
  }
}
