package scalaDemo.sort

object BinarySearch {
  def main(args: Array[String]): Unit = {
    val array = Array(1, 3, 5, 7, 9, 11, 13, 15)

    val index = binarySearch(array, 0, array.length - 1, 14)

    if (index >= 0) {
      println(s"index=$index value=${array(index)}")
    } else {
      println(s"index=$index：值不存在...")
    }

  }

  def binarySearch(arr: Array[Int], left: Int, right: Int, value: Int): Int = {
    var i = left
    var j = right
    var mid: Int = 0

    if (i > j || value < arr(i) || value > arr(j)) {
      return -1
    }

    mid = (i + j) / 2
    if (value < arr(mid)) {
      binarySearch(arr, i, mid - 1, value)
    } else if (value > arr(mid)) {
      binarySearch(arr, mid + 1, j, value)
    } else {
      mid
    }
  }
}
