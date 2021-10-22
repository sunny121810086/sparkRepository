package scalaDemo.abstractDemo.demo01

object AnimalTest01 {
  def main(args: Array[String]): Unit = {
    //临时创建一个匿名子类
    val dog = new Animal01 {
      override var name: String = "dog"
      override var sex: String = "雌性"

      override def describ(): String = {
        s"name: $name\nsex: $sex"
      }

      override def action(): Unit = {
        println("正在追赶小偷...")
      }
    }

    val cat = new Animal01 {
      override var name: String = "cat"
      override var sex: String = "雄性"

      override def describ(): String = {
        s"name: $name\nsex: $sex"
      }

      override def action(): Unit = {
        println("正在抓老鼠...")
      }
    }

    println(dog.describ())
    dog.action()

    println(cat.describ())
    cat.action()
  }

}
