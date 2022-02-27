package huawei.easy

import scala.io._


/**
 * @Author: qwerdf@QAQ
 * @Description:  图片整理
  *              Lily上课时使用字母数字图片教小朋友们学习英语单词，每次都需要把这些图片按照大小（ASCII码值从小到大）排列收好。请大家给Lily帮忙，通过代码解决
 * @Date: 2022/2/27
 * @Param null:
 * @return: null
 **/

object Demo18 {
  def main(args: Array[String]): Unit = {
    val str1 = StdIn.readLine()
    val str2 = StdIn.readLine()
    val arr1 = str1.toCharArray
    val arr2 = str2.toCharArray
    quickSort(arr1,0,arr1.length-1)
    quickSort(arr2,0,arr2.length-1)
    arr1.foreach(x => print(x))
    print("\n")
    arr2.foreach(x => print(x))
  }
  def quickSort(arr: Array[Char],left: Int, right: Int): Unit = {
    var i = left
    var j = right
    var temp = 0

    if(i <= j ) {
      temp = arr(i).toInt
      while (i != j) {
        while (i < j && temp <= arr(j).toInt) {
          j = j-1
        }
        arr(i) = arr(j)

        while (i < j && arr(i).toInt <= temp) {
          i = i+1
        }
        arr(j) = arr(i)
      }
      arr(i) = temp.toChar
      quickSort(arr,left,i-1)
      quickSort(arr,j+1,right)
    }
  }
}
