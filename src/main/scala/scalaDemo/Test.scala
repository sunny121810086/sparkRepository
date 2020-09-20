package scalaDemo
import scala.collection.mutable

object Test {
  def main(args: Array[String]): Unit = {
    val list1 = List("洞庭湖","鄱阳湖","太湖","洪泽湖","巢湖","山川","河流","大海","乡村","淮河","长江","湖泊","荒漠","唯美","二次元","复古")
    val list2 = List("洞庭湖","鄱阳湖","太湖","洪泽湖","巢湖","山川","河流","大海","乡村","淮河","长江","湖泊","荒漠","唯美","二次元","复古")
    val listbuffer: mutable.ListBuffer[Tuple2[String,String]]= new mutable.ListBuffer[Tuple2[String,String]]
    for (ls1 <- list1) {
      for (lst2 <- list2) {
        listbuffer += ((ls1,lst2))
      }
    }
   // println(listbuffer)

    val list3 = List(("洞庭湖",1.0),("鄱阳湖",3.0),("巢湖",2.0))
    val res = list3.sortBy(_._2).reverse
    for (i <- 0 to res.length-1) {
      println("i==="+res(i))
    }
  }

}
