package scalaDemo.extendsDemo.demo03

object Constructor {
  def main(args: Array[String]): Unit = {
    /**
      * 执行流程：
      * 1.先调用子类辅助构造器this(height: Double)
      * 2.调用this即子类的主构造器，但是在初始化子类属性之前，必须先调用父类的主构造器
      * 3.调用父类this()即子类的辅助构造器，然后this("Lucy")即父类的主构造器
      * 4.输出结果
      * 父类-主构造器1...
      * 父类-主构造器2...
      * 父类-辅助构造器3...
      * 子类-主构造器1...
      * 子类-主构造器2...
      * 子类-辅助构造器...
      **/
    val son04 = new Son04(172)
  }
}

class Father04(inName: String) {
  var name: String = inName
  var age: Int = _
  var gender: String = _

  println("父类-主构造器1...")

  def this(inName: String, inAge: Int) = {
    this(inName)
    this.age = inAge //实际调用age_$eq(inAge);
    println("父类-辅助构造器1...")
  }

  def this(inName: String, inAge: Int, inGender: String) = {
    this(inName, inAge)
    this.gender = inGender //实际调用gender_$eq(inGender);
    println("父类-辅助构造器2...")
  }

  println("父类-主构造器2...")

  def this() = {
    this("Lucy") //调用主构造器
    println("父类-辅助构造器3...")
  }
}

class Son04() extends Father04() {
  var height: Double = _

  println("子类-主构造器1...")

  def this(height: Double) = {
    this
    this.height = height
    println("子类-辅助构造器...")
  }

  println("子类-主构造器2...")
}