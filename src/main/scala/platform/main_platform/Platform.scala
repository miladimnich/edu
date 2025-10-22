package platform.main_platform

import platform.finance.{Exchange, Token}
import platform.model.Course

object Platform {
  private var _token = new Token(100000, "MP")
  private var _courses: List[Course] = _

  def init(courses: List[Course]): Unit = {
    _courses = courses
  }

  private def scholarshipFormula(grade: Int, priceCourse: Double): Double = grade match {
    case 5 => 1.1 * priceCourse
    case 4 => 1 * priceCourse
    case 3 => 0.9 * priceCourse
    case 2 => 0.8 * priceCourse
    case 1 => 0.7 * priceCourse
    case _ => 0
  }

  private def sendScholarships(currentMonth: Int): Unit = {
    println("\n--------Scholarships------")
    printPlatformBalance()
    for (course <- _courses if course.isActive) {
      val price = course.price
      for (student <- course.students) {
        val avgCourseGrade = course.averageGradePerCourse(student)
        val amount = scholarshipFormula(Math.ceil(avgCourseGrade).toInt, price)
        val token = new Token(amount, "MP")
        student.addToken(token)
        _token = _token.substructAmount(token)
        println(s"   ${student.name} get ${amount.toInt} MP for ${course}")
      }
      printPlatformBalance()
    }
  }

  private def printPlatformBalance(): Unit = {
    println(s"\nPlatform balance: ${_token.Token_inf()}")
  }

  private def collectTuition(currentMonth: Int): Unit = {
    println("\n--- Tuition Collection ---")
    printPlatformBalance()
    Exchange.printExchangeBalance()
    println()
    for (course <- _courses if course.isActive) {

      val price = course.price

      val teacher = course.teacher

      for (student <- course.students) {
        val studentToken = student.getToken
        println(f"student ${student.name}  before buying a course had a balance of ${student.getToken.amount}%.2f")
        if (studentToken.amount < price) {
          val need = price - studentToken.amount
          println(f"${student.name} does not have enough MP for buying course ${course.typeCourse}. Buying $need%.2f MP from Exchange.")
          student.buy(new Token(need, "MP"))
          Exchange.printExchangeBalance() // after buy
          student.printStudentBalance() // after buy
        }

        student.payForCourse(price)
        println(f"after buying a course ${course.typeCourse} for price $price%.2f MP the balance became ${student.getToken.amount}%.2f tokens  (Teacher: ${teacher.name})")

        _token = _token.addAmount(new Token(price, "MP"))
        printPlatformBalance()
        Exchange.printExchangeBalance()
      }
    }
  }

  def nextMonth(currentMonth: Int): Unit = {
    println(s"\n====== Month ${currentMonth} ======")
    for (course <- _courses) {
      course.activate(currentMonth)

      if (course.startMonth == currentMonth)
        println(s"Course ${course.typeCourse} starts this month ($currentMonth)!")
      if (course.endMonth == currentMonth)
        println(s"Course ${course.typeCourse} ends this month ($currentMonth)!")
    }
    sendScholarships(currentMonth)
    collectTuition(currentMonth)
  }

}