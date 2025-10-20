package platform.model

import platform.enums.CourseType

import scala.collection.mutable


class Student(id: Int, name: String, address: Address) extends Human(id, name, address) {

  val gradesPerCourse: mutable.Map[CourseType, mutable.ListBuffer[Int]] = mutable.Map()


  def assignGrade(courseType: CourseType, grade: Int): Unit = {
    val grades = gradesPerCourse.getOrElseUpdate(courseType, mutable.ListBuffer())
    grades += grade
  }

 

  override def toString: String = s"Student(id=$id, name=$name, address = $address, token = $getToken)"
}
