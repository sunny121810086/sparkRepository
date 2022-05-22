package scalaDemo.boundType

object BoundTypeV1 {
  def main(args: Array[String]): Unit = {
    val pad = new Pad("USA", "平板电脑", "苹果", "ipad")
    val phone = new Phone("China", "5G手机", "华为", "Mate30")
    val huaWeiPhone = new HuaWeiPhone("5G手机", "华为", "Mate30", "China", "Mate30_Pro_8G", 6399)
    val iphone = new Iphone("5G手机", "苹果", "iphoneSE", "USA", "iphoneSE2_4G", 2799)

    upBoundMethod[Phone](huaWeiPhone)
    upBoundMethod[Phone](iphone)
    //upBoundMethod[Phone](pad)   //编译提示：方法入参类型不匹配,应该为泛型Phone的类型或者子类
    //upBoundMethod[ElecGoods](phone) //泛型错误，当前上边界值为Phone类型，泛型A应该为Phone类、HuaWeiPhone类、Iphone类

    downBoundMethod[Phone](huaWeiPhone)
    downBoundMethod[Phone](iphone)
    // downBoundMethod[Phone](pad) //编译提示：方法入参类型不匹配,应该为泛型Phone的类型或者子类
    //downBoundMethod[HuaWeiPhone](huaWeiPhone) //泛型错误,当前下边界值为Phone类型,泛型B应该为Phone类、ElecGoods类
  }

  //上边界 A类型为Phone类及其子类，而该方法入参类型为A类型及其A类型的子类对象
  def upBoundMethod[A <: Phone](elecGoods: A): Unit = {
    if (elecGoods.getClass == classOf[HuaWeiPhone]) {
      val huaWeiPhone = elecGoods.asInstanceOf[HuaWeiPhone]
      println(huaWeiPhone)
    } else if (elecGoods.getClass == classOf[Iphone]) {
      val iphone = elecGoods.asInstanceOf[Iphone]
      println(iphone)
    }
  }

  //下边界 B类型为Phone类及其父类，而该方法入参类型为B类型及其B类型的子类对象,因为B类型的子类不仅仅只有Phone一个
  def downBoundMethod[B >: Phone](elecGoods: B): Unit = {
    if (elecGoods.isInstanceOf[Phone]) {
      val phone = elecGoods.asInstanceOf[Phone]
      println(phone)
    }
  }

}

abstract class ElecGoods {
  var categary: String
  var brand: String
  var model: String

  def function: String
}

class Pad extends ElecGoods {
  var madeIn: String = _
  override var categary: String = _
  override var brand: String = _
  override var model: String = _

  def this(madeIn: String, categary: String, brand: String, model: String) = {
    this()
    this.categary = categary
    this.brand = brand
    this.model = model
    this.madeIn = madeIn
  }

  override def function: String = {
    s"电影、听音乐、玩游戏..."
  }

  override def toString = s"Phone($categary, $brand, $model, $madeIn)"
}

class Phone extends ElecGoods {
  var madeIn: String = _
  override var categary: String = _
  override var brand: String = _
  override var model: String = _

  def this(madeIn: String, categary: String, brand: String, model: String) = {
    this()
    this.categary = categary
    this.brand = brand
    this.model = model
  }

  override def function: String = {
    s"通话、听音乐、玩游戏..."
  }

  override def toString = s"Phone($categary, $brand, $model, $madeIn)"
}

class HuaWeiPhone(var detailModel: String) extends Phone {
  private var price: Double = _

  def this(categary: String, brand: String, model: String, madeIn: String, detailModel: String) = {
    this(detailModel)
    this.categary = categary
    this.brand = brand
    this.model = model
    this.madeIn = madeIn
  }

  def this(categary: String, brand: String, model: String, madeIn: String, detailModel: String, price: Double) = {
    this(categary, brand, model, madeIn, detailModel)
    this.price = price
  }

  override def toString = s"HuaWeiPhone($categary, $brand, $model, $madeIn, $detailModel, $price)"
}

class Iphone(var detailModel: String) extends Phone {
  private var price: Double = _

  def this(categary: String, brand: String, model: String, madeIn: String, detailModel: String) = {
    this(detailModel)
    this.categary = categary
    this.brand = brand
    this.model = model
    this.madeIn = madeIn
  }

  def this(categary: String, brand: String, model: String, madeIn: String, detailModel: String, price: Double) = {
    this(categary, brand, model, madeIn, detailModel)
    this.price = price
  }

  override def toString = s"Iphone($categary, $brand, $model, $madeIn, $detailModel, $price)"
}