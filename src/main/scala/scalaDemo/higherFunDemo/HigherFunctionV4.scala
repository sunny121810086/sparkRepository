package scalaDemo.higherFunDemo

object HigherFunctionV4 {
  def main(args: Array[String]): Unit = {
    val res01 = hFuncA(funcA,"Lucy.jpg",".jpg")
    val res02 = hFuncA(funcA,"sunny",".jpg")
    println(res01)
    println(res02)

    println(hFuncB(".jpg")("@sunny"))

    //闭包可以保留上次引用的某个值，传入一次可以反复使用
    val endFunc = hFuncB(".jpg")
    val list = List("Lucy.jpg","sunny","@sunny")
    val res04 = list.map(endFunc) //.jpg传入一次，map遍历时多次使用
    println(res04)

  }

  def funcA(file: String, end: String): String = {
    if(file.endsWith(end)) {
      file
    } else
      file + end
  }

  def hFuncA(f: (String, String) => String, file: String, end: String): String = {
    funcA(file,end)
  }

  //这里匿名函数和end参数形成一个闭包
  def hFuncB(end: String) = {
    file: String => {
      if(file.endsWith(end))
        file
      else
        file + end
    }
  }
}
