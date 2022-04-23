package scalaDemo

object CircleContinue {
  def main(args: Array[String]): Unit = {
    //方式1：通过循环守卫实现continue效果
    for (i <- 1 to 10 if i % 2 == 0) {
      println(s"i==$i")
    }

    var j = 0
    while (j <= 10) {
      //方式2：通过if表达式实现continue效果
      if (j % 2 != 0) {
        println(s"j==$j")
      }
      j +=1
    }
  }
}
