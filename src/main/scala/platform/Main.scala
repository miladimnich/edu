package platform

import platform.enums.CourseType
import platform.model.{Address, Course, Exchange, Human, Platform, Student, Teacher, Token}
import platform.service.ReadData

import scala.collection.mutable.ListBuffer
import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    val rand = new Random()
    val reader = new ReadData
    val teacherJson = reader.readJsonArray("teachers_list.json").arr
    val studentsJson = reader.readJsonArray("students_list.json").arr
    val addressJson = reader.readJsonArray("address_list.json").arr

    val allTeachers = ListBuffer[Teacher]()
    val allStudents = ListBuffer[Student]()
    val allCourses = ListBuffer[Course]()



    // initialisation courses
    for (course <- CourseType.values) {
      val randomPriceForCourse = 1000 + rand.nextInt(4000) // 1000-5000
      allCourses += Course(course, randomPriceForCourse)
    }
    //initialisation teachers and students
    for (t <- teacherJson.indices) {
      val randomIndex = rand.nextInt(addressJson.size)
      allTeachers += new Teacher(t, teacherJson(t)("name").str, new Address(addressJson(t)("country").str, addressJson(t)("city").str))
      allStudents += new Student(t, studentsJson(t)("name").str, new Address(addressJson(randomIndex)("country").str, addressJson(randomIndex)("city").str))
    }

    allTeachers.foreach(t => println(s"id ${t.id} ${t.name} lives in ${t.address}"))
    println("-------------------------------------------")
    allStudents.foreach(s => println(s"id ${s.id} ${s.name} lives in ${s.address}"))



    //assign students to the course
    for (course <- allCourses) {
      val randomTeacher = rand.nextInt(allTeachers.size)
      val teacher = allTeachers(randomTeacher)
      course.setTeacher(teacher)

      var numberOfStudents = rand.nextInt(allStudents.size) + 1 // At least 1 student
      while (numberOfStudents > 0) {
        val randomStudent = rand.nextInt(allStudents.size) // random index for student
        val randomGrade = 1 + rand.nextInt(5) // random grade 1-5
        val student = allStudents(randomStudent) // fetched student by random index
        course.generateGrade(student, randomGrade)
        numberOfStudents -= 1
      }
      println(s"\nCourse: ${course.typeCourse} price for the Course ${course.price} teacher ${course.teacher.name}")
      println("Assigned Students and grades: ")
      for (student <- course.students) {
        val gradesStr = student.gradesPerCourse.get(course.typeCourse) match {
          case Some(grades) => grades.mkString(", ")
          case None => "No grades"
        }
        println(s"    Student: ${student.name} | Grades: $gradesStr")
      }
    }

    Platform.init(allCourses.toList)
    Platform.sendScholarships()
    Platform.printPlatformBalance()

    val token1 = new Token(10, "MP")
    val token2 = new Token(3, "MP")
    val human = new Human(1, "Masha", new Address(addressJson(0)("country").str, addressJson(0)("city").str))

    human.setToken(token1) //10
    human.sell(token2) //3 10-3=7

    println(human.getToken.Token_inf())

    Exchange.init(10000, human.getToken)
    human.buy(token2)
    println(human.getToken)

  }
}

