package scalaDemo.currency

//日元
class Yen extends Currency {
  var price: Double = _
  private var symbol: String = "￥"

  def this(price: Double) = {
    this()
    this.price = price
  }

  def this(price: Double, symbol: String) = {
    this(price)
    this.symbol = symbol
  }

  override def toString: String = s"$price$symbol"
}

object Yen {
  def apply: Yen = new Yen()

  def apply(price: Double): Yen = new Yen(price)
}