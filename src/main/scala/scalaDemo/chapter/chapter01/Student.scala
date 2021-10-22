package scalaDemo.chapter.chapter01

class Student(name: String, var age: Int) {
  def stuInfo ={
    println(s"name=$name age=$age country=${Student.country}")
  }

}

object Student {
  val country: String ="China"

  def main(args: Array[String]): Unit = {
    val stu1 = new Student("赵敏",20)
    val stu2 = new Student("张无忌",22)
    stu1.stuInfo
    stu2.stuInfo
  }
}
