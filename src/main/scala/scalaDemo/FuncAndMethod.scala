package scalaDemo

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object FuncAndMethod {
  def main(args: Array[String]): Unit = {
    //定义一个函数
    val fun1: (Int, Int) => Int = (x, y) => x + y
    val fun2 = (x: Int, y: Int) => x + y
    val fun2_2 = (x: Int) => x  //入参只有一个时，括号不能少
    val fun3: () => String = () => "飞雪连天射白鹿"

    println(fun3()) //参数列表为空时,调用fun3()括号不能省略
    method5(println("无参函数1")) //方法中参数列表为空可以省略括号
    method6("无参函数2")
    val function7 = method7(".jpg")
    println(function7("girlfriend.jpg"))
    println(function7("boyfriend"))

    method2("笑书神侠倚碧鸳")

    val hmap = new mutable.HashMap[String, ArrayBuffer[String]]()
    val buffer1 = ArrayBuffer[String]()
    val buffer2 = ArrayBuffer[String]()
    buffer1 += "飞狐外传"
    buffer1 += "飞狐外传"
    buffer1 += "雪山飞狐"
    buffer1 += "连城诀"
    buffer1 += "天龙八部"
    buffer1 += "射雕英雄传"
    buffer1 += "白马啸西风"
    buffer1 += "鹿鼎记"
    buffer1 += "笑傲江湖"
    buffer1 += "书剑恩仇录"
    buffer1 += "神雕侠侣"
    buffer1 += "侠客行"
    buffer1 += "倚天剑"
    buffer1 += "碧血剑"
    buffer1 += "鸳鸯刀"
    buffer2 += ("武林外史", "绝代双骄", "楚留香传奇", "多情剑客无情剑", "萧十一郎", "流星蝴蝶剑", "边城浪子",
      "陆小凤传奇", "孔雀翎", "天涯明月刀", "三少爷的剑", "圆月弯刀", "小李飞刀", "浣花洗剑录")
    hmap += ("金庸" -> buffer1, ("古龙", buffer2))
    println(hmap)

    val res1 = method3("神雕侠侣", hmap)
    val res2 = method3("流星蝴蝶剑", hmap)
    val res3 = method3("三国演义", hmap)
    println(res1)
    println(res2)
    println(res3)


    val res4 = method4("小李飞刀", hmap, fun4)
    val res5 = method4("神雕侠侣", hmap, fun4)
    val res6 = method4("西游记", hmap, fun4)
    println(res4)
    println(res5)
    println(res6)

    val grep1: ArrayBuffer[String] = grep(buffer1)
    println(grep1)

    //for过滤器函数
    val grepFunc = (books: ArrayBuffer[String], grepPattern: String) => {
      if (books != null) {
        val greps: ArrayBuffer[String] = for {
          book <- books
          if (book.contains(grepPattern))
        } yield book
        println(greps)
      }
    }

    grepFunc(buffer1, "剑")
  }

  //for过滤器方法
  def grep(books: ArrayBuffer[String]): ArrayBuffer[String] = {
    var greps: ArrayBuffer[String] = null
    if (books != null) {
      greps = for {
        book <- books
        if (book.length == 3)
      } yield book
    }
    greps
  }


  //定义一个方法
  def method1(x: Int, y: Int): Int = {
    x + y
  }

  def method2(str: String): Unit = {
    if (!str.isEmpty && str != null) {
      println(str)
    }
  }

  def method3(bookName: String, bookStore: mutable.Map[String, ArrayBuffer[String]]): String = {
    var tmp_author: String = "未知作者"
    if (!bookName.isEmpty || bookName != null) {
      for (map <- bookStore) {
        val author = map._1
        val bookArray = map._2
        if (bookArray.contains(bookName)) {
          tmp_author = author
        }
      }
    }
    tmp_author
  }

  def method4(bookName: String, bookStore: mutable.Map[String, ArrayBuffer[String]], fun: (String, mutable.Map[String, ArrayBuffer[String]]) => String): String = {
    val value = fun(bookName, bookStore)
    value
  }


  val fun4: (String, mutable.Map[String, ArrayBuffer[String]]) => String = (bookName, bookStore) => {
    var tmp_author: String = "未知作者"
    if (!bookName.isEmpty || bookName != null) {
      for (map <- bookStore) {
        val author = map._1
        val bookArray = map._2
        if (bookArray.contains(bookName)) {
          tmp_author = author
        }
      }
    }
    tmp_author
  }

  def method5(f: => Unit) = {
    f
  }

  def method6(f: => String) = {
    println(f)
  }

  def method7(suffix: String) = {
    (fileName: String) => {
      if (fileName.endsWith(suffix)) {
        fileName
      } else {
        fileName + suffix
      }
    }
  }

}
