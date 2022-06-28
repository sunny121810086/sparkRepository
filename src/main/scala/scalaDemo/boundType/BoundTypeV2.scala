package scalaDemo.boundType


object BoundTypeV2 {
  def main(args: Array[String]): Unit = {
    val class01 = new EnglishClass[SeasonEnmu.SeasonEnmu,String,String](SeasonEnmu.spring,"1001","初级班")
    println(class01.getInfo)
  }
}

//给类添加泛型
class EnglishClass[A, B, C](val season: A, val cName: B, val cType: C) {
  def getInfo = s"season：$season\ncName：$cName\ncType：$cType"
}

//枚举类型
object SeasonEnmu extends Enumeration {
  type SeasonEnmu = Value
  val spring,summer,autumn,winter = Value
}

