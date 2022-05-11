package sparkCore.Demo01

import java.io.ObjectInputStream
import java.net.ServerSocket

object Executor1 {
  def main(args: Array[String]): Unit = {
    //启动服务器，准备接受数据
    val server = new ServerSocket(8888)

    println("服务器1启动...")

    val client = server.accept()
    val in = client.getInputStream
    val objIn = new ObjectInputStream(in)

    val task = objIn.readObject().asInstanceOf[SubTask]
    val list = task.compute()

    println(s"计算结果1：$list")

    objIn.close()
    client.close()
    server.close()

  }
}
