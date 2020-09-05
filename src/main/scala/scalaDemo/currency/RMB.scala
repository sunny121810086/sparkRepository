package scalaDemo.currency

class RMB extends Currency {
  var price: Double = _
  private var symbol: String = "RMB"

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

object RMB {
  def apply(): RMB = new RMB()

  def apply(price: Double): RMB = new RMB(price)
}
