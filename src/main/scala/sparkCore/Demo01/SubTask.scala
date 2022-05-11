package sparkCore.Demo01

class SubTask extends Serializable {
  var datas: List[Int] = null
  var logic: Int => Int = null

  def compute() = {
    datas.map(logic)
  }
}
