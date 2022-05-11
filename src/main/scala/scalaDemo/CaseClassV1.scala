package scalaDemo

object CaseClassV1 {
  def main(args: Array[String]): Unit = {
    val arr = Array(
      Teacher01A("张三", 33), Teacher01A("李四", 26),
      Student01A("小明", 9), Student01A("小红", 19),
      Stranger01A
    )

    for (person <- arr) {
      println(matchCase(person))
    }
  }

  def matchCase(person: AnyRef) = {
    person match {
      case Teacher01A(name, age) => s"name=$name|age=$age 允许教师进入..."
      case Student01A(name, age) => {
        if (age < 18)
          s"name=$name|age=$age 允许未成年学生进入..."
        else
          s"name=$name|age=$age 允许成年学生进入..."
      }
      case Stranger01A => s"禁止陌生人进入!!!"
      case _ => s"错误类型匹配!!!"
    }
  }
}

/**
  * 1.case类是一种特殊的类，它们经过优化以被用于模式匹配，构造参数默认为val;
  * 2.当定义一个类时，如果在class关键字前加上case关键字，则该类称为case类;
  * 3.Scala为case类自动重载了许多实用的方法，包括toString、equals和hashcode方法;
  * 4.Scala为每一个case类自动生成一个伴生对象，其包括模板代码1个apply方法，
  * 因此，实例化case类的时候无需使用new关键字。和1个unapply方法，
  * 该方法包括一个类型为伴生类的参数返回的结果是Option类型，对应的类型参数是N元组，
  * N是伴生类中主构造器参数的个数。Unapply方法用于对对象进行解构操作，
  * 在case类模式匹配中，该方法被自动调用，并将待匹配的对象作为参数传递给它。
  **/

case class Teacher01A(name: String, age: Int)

case class Student01A(name: String, age: Int)

object Stranger01A