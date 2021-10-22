package scalaDemo.overrideDemo

class SonA(firstName: String, lastName: String) extends FatherA(lastName) {
  private var sonLover: String = _

  println(s"子类的主构造器~~~~$sonLover")

  def this(firstName: String, lastName: String, sonLover: String) {
    this(firstName, lastName)
    this.sonLover = sonLover
    println(s"子类的辅助构造器~~~~$sonLover")
  }

  def sonMethod = {
    println(s"子类特有的方法~~~~")
  }
}
