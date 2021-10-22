package scalaDemo.implicits
import scala.collection.mutable
object test {
  def main(args: Array[String]): Unit = {
    val mmap = mutable.Map.empty[String, Int]
    val mhmap = mutable.HashMap.empty[String, Int]
    val mset2 = mutable.Set.empty[String]
    val mset3: mutable.Set[String] = mutable.Set()
    val mhset = mutable.HashSet.empty[String]
    val list = mutable.ListBuffer.empty[String]
    val list2 = mutable.ListBuffer[String]()
    mset3.add("sunny")
    list2 += "1992"
    println(mset3)
    println(list2)
  }
  def sayOk(): Unit = {
    println("ok")
  }
}
