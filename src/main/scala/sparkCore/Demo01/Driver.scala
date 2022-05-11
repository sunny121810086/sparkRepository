package sparkCore.Demo01

import java.io.ObjectOutputStream
import java.net.Socket

object Driver {
  def main(args: Array[String]): Unit = {
    //连接服务器
    val client1 = new Socket("localhost",8888)
   // val client2 = new Socket("localhost",9999)

    val out1 = client1.getOutputStream
    val objOut1 = new ObjectOutputStream(out1)

    val task = new Task
    val subTask1 = new SubTask
    subTask1.datas = task.datas.take(2)
    subTask1.logic = task.logic

    objOut1.writeObject(subTask1)
    objOut1.flush()
    objOut1.close()
    client1.close()
  }
}
