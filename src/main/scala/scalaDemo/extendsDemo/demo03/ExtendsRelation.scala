package scalaDemo.extendsDemo.demo03

object ExtendsRelation {
  def main(args: Array[String]): Unit = {
    val son01 = new Son01
    val grandSon01 = new GrandSon01
    son01.sonInfo
    grandSon01.grandSonInfo
  }
}

//1.在scala中,子类继承了父类所有属性,但是private修饰的属性和方法在子类中无法访问,只能在本类和伴生对象中使用
//2.protected修饰的属性和方法在子类中可以访问
//3.protected,private修饰的除了上述位置,不能在同包的其他位置被访问
class Father01 {
  var name: String = "Lucy"
  protected var gender: String = "female"
  private var age: Int = 18

  def getBaseInfo = {
    println(s"name=$name gender=$gender age=$age")
  }

  protected def getJobInfo = {
    println("this is protected info...")
  }

  private def getSalaryInfo = {
    println("this is private info...")
  }
}

class Son01 extends Father01 {
  def sonInfo = {
    this.name = "LucySon01" //可访问--this.name_$eq("LucySon01")
    this.gender = "male" //可访问--this.gender_$eq("LucySon01")
    // this.age = 0  --无法访问
    println(s"Son01: ${this.name} ${this.gender}")
  }
}

class GrandSon01 extends Son01 {
  def grandSonInfo = {
    this.name = "LucyGrandSon01"
    this.gender = "male"
    // this.age = 0  --无法访问
    println(s"GrandSon01: ${this.name} ${this.gender}")
  }
}
