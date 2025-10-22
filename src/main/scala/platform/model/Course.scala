package platform.model

import platform.enums.CourseType
import platform.finance.Token

import scala.collection.mutable
import scala.util.Random

class Course(var typeCourse: CourseType, var price: Double, val startMonth: Int,
             val endMonth: Int) {
  var teacher: Teacher = _
  val students: mutable.Set[Student] = mutable.Set()
  var isActive: Boolean = false

  def setTeacher(teacher: Teacher): Unit = {
    this.teacher = teacher
  }

  def generateGrade(course: Course, student: Student, grade: Int): Unit = {
    students += student
    var count = grade
    while (count > 0) {
      val randomGrade = 1 + Random.nextInt(5)
      student.assignGrade(course.typeCourse, randomGrade)
      count -= 1
    }
  }

  def averageGradePerCourse(student: Student): Double = {
    student.gradesPerCourse
      .get(typeCourse)
      .filter(_.nonEmpty)
      .map(grades => grades.sum.toDouble / grades.size)
      .getOrElse(0.0)
  }

  def activate(currentMonth: Int): Unit = {
    if (currentMonth >= startMonth && currentMonth <= endMonth)
      isActive = true
    else
      isActive = false
  }

  def getPriceToken: Token = new Token(price.toInt, "MP")

  override def toString: String = s"Course : $typeCourse"

}
