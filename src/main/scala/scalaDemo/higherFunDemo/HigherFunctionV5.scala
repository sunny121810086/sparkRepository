package scalaDemo.higherFunDemo

object HigherFunctionV5 {
  def main(args: Array[String]): Unit = {
    val str1 = "Lucy"
    val str2 = "@Lucy"
    val res01 = str1.checkEq(str2)(eq)
    println(res01)
  }

  /**
    *函数柯里化：
    * 将比较字符串分成两个任务完成：
    * 1.checkEq 完成转化大小写
    * 2.函数f 完成比较任务
    *
   **/
  implicit class TestEq(s: String) {
    def checkEq(ss: String)( f: (String, String) => Boolean) = {
      f(s.toLowerCase,ss.toLowerCase)
    }
  }

  def eq(s1: String, s2: String): Boolean = {
    s1.equals(s2)
  }
}
