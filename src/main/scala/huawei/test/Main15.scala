package huawei.test

import scala.io.StdIn
import scala.collection.mutable

//数据分类处理
object Main15 {
  def main(args: Array[String]): Unit = {
    var line: String = null
    var flag: Boolean = true
    val arr = new Array[String](2)
    var i = 0
    var res = ""
    val resArr = new mutable.ArrayBuffer[String]()
    var num = 0
    while ({line = StdIn.readLine(); line != null}) {
      if (flag) {
        arr(i) = line
        i +=1
        if (i == 2) {
          flag = false
        }
      }

      if (i == 2) {
        val arrI = arr(0).split(" ")
        val arrR = arr(1).split(" ")
        val set: mutable.Set[String] = mutable.Set.empty
        for (index <- 1 until arrR.length) {
          set.add(arrR(index))
        }
//        println(arrI.toBuffer)
//        println(arrR.toBuffer)

        val sortArr = set.toArray.sortBy(_.toInt)
//        println(sortArr.toBuffer)

        sortArr.foreach {
          x => {
            for (k <- 1 until arrI.length) {
              if (arrI(k).contains(x)) {
                resArr += s"${k-1} ${arrI(k)}"
                num += 1
              }
            }
//            println("resArr==" +resArr)
            if (num > 0 ) {
              res = res + s" ${x} ${num} ${resArr.mkString(" ")}"
            }
            num = 0
            resArr.clear()
//            println("res=="+res)
          }
        }

        res = s"${res.trim.split(" ").length}" + res

        println(res)

        i = 0
        flag = true
        res = ""
      }
    }
  }
}
