package scalaDemo

import java.text.SimpleDateFormat
import java.util.Date

object TimeUtils {
  def main(args: Array[String]): Unit = {
    val day = dayFormat(1596961296066L)
    val day2 = nowdayFormat(new Date())
    println(day2)

  }

  //long类型 --> 日期
  def dayFormat(longTime: Long): String = {
    val date = new Date(longTime)
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val day = dateFormat.format(date)
    day
  }

  //当前时间 --> 日期
  def nowdayFormat(date: Date) = {
    println(s"date: $date")
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val day = dateFormat.format(date)
    day
  }
}
