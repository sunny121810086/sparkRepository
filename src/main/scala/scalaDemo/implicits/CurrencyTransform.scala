package scalaDemo.implicits

import scalaDemo.currency.{Currency, Dollar, RMB, Yen}


object CurrencyTransform {

  //定义一个人民币转其他货币类型的汇率
  private [this] val rmb2dollar_exchange_rate = 0.1439
  private [this] val rmb2jpy_exchange_rate = 15.3365

  implicit class CurrencyTransformation( var currency: Currency) {
    def rmbToDollar(currency: RMB): Dollar = {
      var tmp: Dollar = null
      if (currency.isInstanceOf[RMB]) {
        val rmb = currency.asInstanceOf[RMB]
        val price = rmb.price*rmb2dollar_exchange_rate
        tmp = Dollar(price)
      }
      tmp
    }

    def rmbToJpy(currency: Currency): Yen = {
      var tmp: Yen = null
      if (currency.getClass == classOf[RMB]) {
        val rmb = currency.asInstanceOf[RMB]
        val price = rmb.price*rmb2jpy_exchange_rate
        tmp = Yen(price)
      }
      tmp
    }
  }

}