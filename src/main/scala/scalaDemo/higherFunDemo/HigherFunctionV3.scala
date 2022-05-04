package scalaDemo.higherFunDemo

object HigherFunctionV3 {
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4,5)
    val res01 = list.map((x: Int) => x + 10) //map是一个高阶函数，可以直接传入匿名函数
    val res02 = list.map((x) => x + 10) //由于list全是Int类型，在这里可推导出具体类型，所有可省略具体类型
    val res03 = list.map(x => x + 10) //参数只有一个，可省略()
    val res04 = list.map(_ + 10) //由于参数x在 => 右边只出现1次，可用_代替
    println(res04)

    val lis01 = list.reduce((x: Int,y: Int) => x + y)
    val lis02 = list.reduce((x,y) => x + y)
    val lis03 = list.reduce(_+_) //x和y在 => 右边只出现1次，可用_代替
    println(lis03)
  }
}
