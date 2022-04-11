package huawei.easy

import scala.io._
import scala.collection.mutable

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 有一种技巧可以对数据进行加密，它使用一个单词作为它的密匙。下面是它的工作原理：首先，选择一个单词作为密匙，如TRAILBLAZERS。如果单词中包含有重复的字母，只保留第1个，将所得结果作为新字母表开头，并将新建立的字母表中未出现的字母按照正常字母表顺序加入新字母表。如下所示：
  * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
  *
  * T R A I L B Z E S C D F G H J K M N O P Q U V W X Y (实际需建立小写字母的字母表，此字母表仅为方便演示）
  *
  * 上面其他用字母表中剩余的字母填充完整。在对信息进行加密时，信息中的每个字母被固定于顶上那行，并用下面那行的对应字母一一取代原文的字母(字母字符的大小写状态应该保留)。因此，使用这个密匙， Attack AT DAWN (黎明时攻击)就会被加密为Tpptad TP ITVH。
  *
  * 请实现下述接口，通过指定的密匙和明文得到密文。
  *
  * 数据范围：1 \le n \le 100 \1≤n≤100  ，保证输入的字符串中仅包含小写字母
  *
  * 输入描述：
  * 先输入key和要加密的字符串
  *
  * 输出描述：
  * 返回加密后的字符串
  *
  * 示例1
  * 输入：
  * nihao
  * ni
  * 输出：
  * le
  *
 * @Date: 2022/4/11
 * @Param null:
 * @return: null
 **/

object Demo38 {
  def main(args: Array[String]): Unit = {
    var i = 0
    val arr = new Array[String](2)
    var line: String = null
    while({line = StdIn.readLine(); line !=null}) {
      if(i <=1) {
        arr(i) = line
        i = i+1
      }

      if(i ==2) {
        val s1 = arr(0)
        val s2 = arr(1)
        val mset = mutable.Set[String]()
        val array = new Array[String](26)
        val tmpArr = Array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
        var k = 0
        var tmpStr = ""
        var tmpStr2 = ""

        for(j <- 0 to s1.length-1) {
          tmpStr = s1.charAt(j).toString.toUpperCase()
          if(!mset.contains(tmpStr)) {
            mset += tmpStr
            array(k) = tmpStr
            k = k+1
          }
        }


        tmpArr.foreach(x => {
          if(!mset.contains(x)) {
            mset += x
            array(k) = x
            k = k+1
          }
        })

        val mmap = mutable.Map[String, String]()
        var key = ""
        var value = ""
        for(y <- 0 to 25) {
          key = tmpArr(y)
          value = array(y)
          mmap += key -> value
        }

        var res = ""
        var ss = ""
        for(z <- 0 to s2.length-1 ) {
          tmpStr2 = s2.charAt(z).toString
          if(tmpStr2 != " " && tmpStr2.matches("[A-Z]")) {
            ss = mmap.getOrElse(tmpStr2,"-1")
            res = res + ss
          } else if(tmpStr2 != " " && tmpStr2.matches("[a-z]")) {
            ss = mmap.getOrElse(tmpStr2.toUpperCase,"-1")
            res = res + ss.toLowerCase()
          } else {
            res = res + " "
          }
        }

        println(res)

        i=0
      }
    }
  }
}
