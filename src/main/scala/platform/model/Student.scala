package platform.model

import platform.enums.CourseType
import platform.finance.Token

import scala.collection.mutable


class Student(id: Int, name: String, address: Address) extends Human(id, name, address) {

  val gradesPerCourse: mutable.Map[CourseType, mutable.ListBuffer[Int]] = mutable.Map()


  def assignGrade(courseType: CourseType, grade: Int): Unit = {
    val grades = gradesPerCourse.getOrElseUpdate(courseType, mutable.ListBuffer())
    grades += grade
  }

  def payForCourse(price: Double): Unit = {
    val payment = new Token(price, "MP")
    val currentBalance = getToken

    if (currentBalance.amount >= price) {
      val newBalance = currentBalance.substructAmount(payment)
      setToken(newBalance)
    } else {
      throw new IllegalArgumentException(s"$name doesn't have enough MP to pay for the course.")
    }
  }

  def printStudentBalance(): Unit = {
    println(s"$name's token balance: ${getToken.Token_inf()}")
  }


  override def toString: String = s"Student(id=$id, name=$name, address = $address, token = $getToken)"
}
