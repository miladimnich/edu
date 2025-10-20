package platform.model

class Grade(val student: Student, val grade: Int) {
  override def toString: String = s"Grade(student =$student, grade = $grade)"

}
