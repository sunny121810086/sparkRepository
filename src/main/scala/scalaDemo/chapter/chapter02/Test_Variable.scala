package scalaDemo.chapter.chapter02


object Test_Variable {
  def main(args: Array[String]): Unit = {
    val stu1 = new Student01("赵敏", 20, "清华大学")

    //1.Student01对象中的name属性访问不到，没有用val或var修饰
    //2.由于age被val修饰，值不可修改。而school被var修饰，值可以修改
    // stu1.age = 18

    stu1.school = "北京大学"

    stu1.stuInfo

    /**
      * 总结：主构造器中name、age、school都会被编译成私有成员变量和构造方法
      * private final String name;
      * private final int age;
      * private String school;
      *
      * public Student01(String name, int age, String school) {}
      *
      * val修饰的变量会生成公有的类似java中的"get"方法
      * public int age() {
      *   return this.age;
      * }
      *
      * val修饰的变量会生成公有的类似java中的"get"方法和 "set"方法
      * scala中用变量名()和变量名_$eq()来表示
      * public String school() {
      *   return this.school;
      * }
      *
      * public void school_$eq(String x$1) {
      *     this.school = x$1;
      * }
      *
      *
      **/


  }

}


class Student01(name: String, val age: Int, var school: String) {
  def stuInfo = {
    println(s"name=$name age=$age school=$school")
  }
}

