package platform.model

 
class Student(id: Int, name: String, address: Address) extends Human(id, name, address) {
  
  private var _grade: Int = 0




  def assignGrade(grade: Int): Unit = {
    _grade = grade
  }
}
