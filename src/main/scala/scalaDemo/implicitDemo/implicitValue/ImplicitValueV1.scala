package scalaDemo.implicitDemo.implicitValue

object ImplicitValueV1 {
  def main(args: Array[String]): Unit = {
    implicit val name: String = "Lucy" //隐式值

    //底层函数名：showInfo$1
    def showInfo(implicit inName: String) = {
      println(s"name is $inName")

      //showInfo$2
      def showInfo() = {
        println("main -> showInfo -> showInfo")
      }
    }
    showInfo
  }

  //showInfo
  def showInfo() = {
    println("object -> showInfo")
  }

}
