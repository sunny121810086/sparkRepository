package scalaDemo.currency

class Dollar extends Currency {
  private var price: Double = _
  private var symbol: String = "$"

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

object Dollar {
  def apply: Dollar = new Dollar()

  def apply(price: Double): Dollar = new Dollar(price)

}
