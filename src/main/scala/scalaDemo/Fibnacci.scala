package scalaDemo

object Fibnacci {
  def main(args: Array[String]): Unit = {
    println(fibn(7))
  }

  /**
   * @Author: qwerdf@QAQ
   * @Description: 斐波那契数列数列从第3项开始，每一项都等于前两项之和
    * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377，610，987，1597 ...
    * 示例： n=7 输出13
    * 第1次入栈：main栈  println(fibn(7))    最后输出：13
    * 第2次    fibn1栈  fibn(7)    fibn(n - 2) + fibn(n - 1)  返回9：5 + 8 = 13
    * 第3次    fibn(n - 2)  = fibn(5)    返回值8： 5
    *          fibn(n - 1) = fibn(6)    返回值7： 8
    * 第4次    fibn(5 - 2) + fibn(5 - 1) = fibn(3) + fibn(4)  返回值6：2+3=5
    *          fibn(6 - 2) + fibn(6 - 1) = fibn(4) + fibn(5) 返回值5：3+5=8
    * 第5次    f(1) + f(2) = 2
    *          f(2) + f(3) = 1 + f(3)   返回值4：1+2=3
    *          f(2) + f(3) = 1 + f(3)   返回值3：1+2=3
    *          f(3) + f(4)    返回值1：2+3=5
    *第6次     f(1) + f(2) = 2
    *          f(1) + f(2) = 2
    *          f(1) + f(2) = 2
    *          f(2) + f(3) = 1 + f(3)   返回值1：1+2=3
    *第7次     f(1) + f(2) = 2
    *
    *备注：先入栈则后出栈
    *
   * @Date: 2022/4/23
   * @Param null:
   * @return: null
   **/

    //递归函数编译阶段无法推断具体的结果类型，必须指定返回值类型
  val fibn: Int => Int = n => {
      if (n == 1 || n == 2) {
        1
      } else {
        fibn(n - 2) + fibn(n - 1)
      }
  }
}
