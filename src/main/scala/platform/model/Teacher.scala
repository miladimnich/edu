package platform.model

class Teacher(id: Int, name: String, address: Address) extends Human(id, name, address) {
  override def toString: String = s"Teacher(id=$id, name=$name, address=$address)"
}
