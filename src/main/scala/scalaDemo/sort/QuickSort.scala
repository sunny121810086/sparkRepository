package scalaDemo.sort

object QuickSort {
  def main(args: Array[String]): Unit = {
    val arr = Array(9, 8, 7, 6, 5, 4, 3, 2, 1)
    quickSort(arr, 0, arr.length - 1)
    arr.foreach(println(_))
  }

  def quickSort(arr: Array[Int], left: Int, right: Int): Unit = {
    var i = left
    var j = right
    var temp = 0

    if (i <= j) {
      temp = arr(left)

      while (i < j) {
        while (i < j && temp <= arr(j)) {
          j -= 1
        }

        arr(i) = arr(j)

        while (i < j && temp >= arr(i)) {
          i += 1
        }

        arr(j) = arr(i)
      }
      arr(i) = temp
      quickSort(arr, left, i - 1)
      quickSort(arr, j + 1, right)
    }
  }
}
