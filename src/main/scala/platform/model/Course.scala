package platform.model

import platform.enums.CourseType

import scala.collection.mutable.ListBuffer
import scala.util.Random

class Course(var typeCourse: CourseType) {
  var teacher: Teacher = _
  val students: ListBuffer[Student] = ListBuffer()
  val grades: ListBuffer[Grade] = ListBuffer()

  def generateGrade(student: Student, maxGrade: Int): Unit = {
    val gradeValue = 1 + Random.nextInt(maxGrade)
    grades += Grade(student, gradeValue)
    student.assignGrade(gradeValue)
  }


}
