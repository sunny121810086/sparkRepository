package scalaDemo

import java.text.SimpleDateFormat
import java.util.Date

object TimeUtils {
  def main(args: Array[String]): Unit = {
    val day = dayFormat(1596961296066L)
    println(day)

  }
  def dayFormat(longTime: Long): String = {
    val date = new Date(longTime)
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val day = dateFormat.format(date)
    day
  }
}
