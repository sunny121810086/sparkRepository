package scalaDemo.currency

object CurrencyTest {
  def main(args: Array[String]): Unit = {

    val rmb = RMB(1000)
    import scalaDemo.implicits.CurrencyTransform._
    //转美元、日元
    val dollar = rmb.rmbToDollar(rmb)
    val yen = rmb.rmbToJpy(rmb)
    println(s"美元：$dollar  日元：$yen")
  }
}
