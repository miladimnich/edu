package platform.model

import platform.enums.CourseType

import scala.collection.mutable
import scala.util.Random

class Course(var typeCourse: CourseType, var price: Double) {
  var teacher: Teacher = _
  val students: mutable.Set[Student] = mutable.Set()


  def setTeacher(teacher: Teacher): Unit = {
    this.teacher = teacher
  }

  def generateGrade(student: Student, grade: Int): Unit = {
    students += student
    var count = grade
    while (count > 0) {
      val randomGrade = 1 + Random.nextInt(5)
      student.assignGrade(typeCourse, randomGrade)
      count -= 1
    }
  }

  def averageGradePerCourse(student: Student): Double = {
    student.gradesPerCourse.get(typeCourse)
    match {
      case Some(grades) if grades.nonEmpty =>
        grades.sum.toDouble / grades.size
      case _ => 0.0
    }
  }

    def getPriceToken(): Token = new Token(price.toInt, "MP")

    override def toString: String = s"Course : $typeCourse"

}
