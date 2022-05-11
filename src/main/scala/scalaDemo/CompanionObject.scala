package scalaDemo

object CompanionObject {
  def main(args: Array[String]): Unit = {
    val person = new Person()
    val student = new Student
    person.name = "鞠婧祎"  //person.name_$eq("鞠婧祎")
    person.gender2 = "女" //person.gender_$eq("女")
   // person.age = 26   //person.age_$eq(26)
    println(classOf[Person])
    println(person.getClass.getName)
  }
}
class Person {
  private val privateDescription: String = "私有属性-人类" //生成的字节码文件中，只有私有的getter方法--pirvate String privateDescription()
  val publicDescription: String = "公有属性-动物" //生成的字节码文件中，只有公有的getter方法--public String publicDescription()
  var name: String = _  //var修饰的变量，编译成的字节码文件中，既有setter方法又有getter方法
  protected var gender1: String = _ //当前类及其子类对象可以访问
  protected[scalaDemo] var gender2: String = _ //String和引用类型的变量，使用"_"初始化时全部为null
  private var age: Int = _  //val修饰的属性，字节码文件只有getter方法，而var修饰的属性，字节码文件既有setter又有getter方法
  println(Person.height) //访问伴生对象的私有属性--类名访问
}

object Person {
  private var height: Double = _
  private val person = new Person
  person.age //访问伴生类私有属性，需要先new一个对象
  //Person.age  //无法通过类名访问
}

class Student extends Person {
  private val person = new Person //子类中无法继承父类中的私有属性
}

/*
public class Person {
  private final String privateDescription = ";

  private String privateDescription() {
    return this.privateDescription;
  }

  private final String publicDescription = ";

  private String name;

  private String gender;

  private int age;

  public String publicDescription() {
    return this.publicDescription;
  }

  public String name() {
    return this.name;
  }

  public void name_$eq(String x$1) {
    this.name = x$1;
  }

  public String gender() {
    return this.gender;
  }

  public void gender_$eq(String x$1) {
    this.gender = x$1;
  }

  private int age() {
    return this.age;
  }

  private void age_$eq(int x$1) {
    this.age = x$1;
  }
}
*/
