package platform.model

object Platform {
  private var _token = new Token(100000000, "MP")
  private var _courses: List[Course] = _
  private var end: Int = 5

  def init(courses: List[Course]): Unit = {
    _courses = courses
  }

  def scholarshipFormula(grade: Int, priceCourse: Double): Double = grade match {
    case 5 => 1.1 * priceCourse
    case 4 => 1 * priceCourse
    case 3 => 0.9 * priceCourse
    case 2 => 0.8 * priceCourse
    case 1 => 0.7 * priceCourse
    case _ => 0
  }

  def sendScholarships(): Unit = {
    println("\n--------Scholarships------")
    for (course <- _courses) {
      val price = course.price
      for (student <- course.students) {
        val avgCourseGrade = course.averageGradePerCourse(student)
        val amount = scholarshipFormula(avgCourseGrade.toInt, price)
        val token = new Token(amount.toInt, "MP")
        student.addToken(token)
        _token= _token.substructAmount(token)
        
        println(s"   ${student.name} get ${amount.toInt} MP for ${course}")

      }
    }
  }

  def printPlatformBalance(): Unit = {
    println(s"\nPlatform balance: ${_token.Token_inf()}")
  }

  // Оплата курсів
//  def collectTuition(): Unit = {
//    println("\n💳 --- Оплата навчання ---")
//    _data.getDataList.foreach { teacher =>
//      val course = teacher.getCourse
//      val price = course.Price
//      course.getListSTD.foreach { std =>
//        if (std.getToken.amount < price.amount) {
//          val need = price.amount - std.getToken.amount
//          println(s"${std.Name} не має достатньо MP. Купує $need MP через Біржу.")
//          val newTokens = new Token(need, "MP")
//          Exchange.buy(newTokens)
//          std.addToken(newTokens)
//        }
//        std.payForCourse(course)
//        _token.addAmount(price)
//        println(s"${std.Name} сплатив ${price.amount} MP за курс ${course.typeCourse}")
//      }
//    }
}

