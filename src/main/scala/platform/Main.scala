package platform

import platform.enums.CourseType
import platform.finance.{Exchange, Token}
import platform.main_platform.Platform
import platform.model.{Address, Course, Student, Teacher}
import platform.service.{ReadData, Simulation}

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
      val startMonth = 1 + rand.nextInt(3)
      val endMonth = startMonth + 3
      allCourses += new Course(course, randomPriceForCourse, startMonth, endMonth)
    }
    //initialisation teachers and students
    for (i <- teacherJson.indices) {
      val randomIndex = rand.nextInt(addressJson.size)
      allTeachers += new Teacher(i, teacherJson(i)("name").str, new Address(addressJson(i)("country").str, addressJson(i)("city").str))
      allStudents += new Student(i, studentsJson(i)("name").str, new Address(addressJson(randomIndex)("country").str, addressJson(randomIndex)("city").str))
    }

    allTeachers.foreach(t => println(s"id ${t.id} ${t.name} lives in ${t.address}"))
    println("-------------------------------------------")
    allStudents.foreach(s => println(s"id ${s.id} ${s.name} lives in ${s.address}"))



    //assign students to the course
    for (course <- allCourses) {
      val randomTeacher = rand.nextInt(allTeachers.size)
      val teacher = allTeachers(randomTeacher)
      course.setTeacher(teacher)

      var numberOfStudents = rand.nextInt(allStudents.size)
      while (numberOfStudents >= 0) {
        val randomStudent = rand.nextInt(allStudents.size) // random index for student
        val randomGrade = 1 + rand.nextInt(5) // random grade 1-5 quantity
        val student = allStudents(randomStudent) // fetched student by random index
        course.generateGrade(course, student, randomGrade)
        numberOfStudents -= 1
      }
      println(s"\nCourse: ${course.typeCourse} price for the Course ${course.price} teacher ${course.teacher.name}")
      println("Assigned Students and grades: ")
      for (student <- course.students) {
        val gradesStr = student.gradesPerCourse.get(course.typeCourse)
          .map(grades => grades.mkString(", "))
          .getOrElse("No grades")
        println(s"    Student: ${student.name} | Grades: $gradesStr")
      }
    }
    Exchange.init(10000, new Token(50000, "MP"))
    Platform.init(allCourses.toList)
    Simulation.runMonths(5)
  }
}

