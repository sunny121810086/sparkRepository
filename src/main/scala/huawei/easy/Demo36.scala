package huawei.easy

import scala.io._

/**
 * @Author: qwerdf@QAQ
 * @Description:
  * 描述
  * 请实现一个计票统计系统。你会收到很多投票，其中有合法的也有不合法的，请统计每个候选人得票的数量以及不合法的票数。
  * （注：不合法的投票指的是投票的名字不存在n个候选人的名字中！！）
  *
  * 数据范围：每组输入中候选人数量满足 1 \le n \le 100 \1≤n≤100  ，总票数量满足 1 \le n \le 100 \1≤n≤100
  * 输入描述：
  * 第一行输入候选人的人数n，第二行输入n个候选人的名字（均为大写字母的字符串），第三行输入投票人的人数，第四行输入投票。
  *
  * 输出描述：
  * 按照输入的顺序，每行输出候选人的名字和得票数量（以" : "隔开，注：英文冒号左右两边都有一个空格！），最后一行输出不合法的票数，格式为"Invalid : "+不合法的票数。
  * 示例1
  * 输入：
  * 4
  * A B C D
  * 8
  * A D E CF A GG A B
  * 输出：
  * A : 3
  * B : 1
  * C : 0
  * D : 1
  * Invalid : 3
  * 说明：
  * E CF GG三张票是无效的，所以Invalid的数量是3.
 * @Date: 2022/4/10
 * @Param null:
 * @return: null
 **/

object Demo36 {
  def main(args: Array[String]): Unit = {
    var i = 0
    val arr = new Array[String](4)
    var line: String = null
    while ( {line = StdIn.readLine(); line != null}) {
      if (i <= 3) {
        arr(i) = line
        i = i + 1
      }
      if (i == 4) {
        var Invalid = 0
        val res = arr(1).split(" ").map((_,0))
        val person = arr(3).split(" ")
        person.foreach(s => {
          var flag = true
          for(i <- 0 to res.length-1 if flag) {
            if(s == res(i)._1) {
              val value = res(i)._2 +1
              res(i) =(res(i)._1,value)
              flag = false
            }
          }
          if(flag) {
            Invalid +=1
          }
        })
        res.foreach(x => {
          val str = x._1 + " : " +x._2
          println(str)
        })
        println("Invalid : "+Invalid)
      }
    }
  }
}
