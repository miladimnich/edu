package platform

import platform.enums.CourseType
import platform.model.{Address, Course, Exchange, Human, Student, Teacher, Token}
import platform.service.ReadData

import scala.collection.mutable.ListBuffer
import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    val reader = new ReadData
    val teacherJson = reader.readJsonArray("teachers_list.json")
    val studentsJson = reader.readJsonArray("students_list.json")
    val addressJson = reader.readJsonArray("address_list.json")

    val allTeachers = ListBuffer[Teacher]()
    val allStudents = ListBuffer[Student]()
    val rand = new Random()
    for (i <- teacherJson.arr.indices) {
      val randomIndex = rand.nextInt(addressJson.arr.size)
      allTeachers += new Teacher(i, teacherJson(i)("name").str, new Address(addressJson(i)("country").str, addressJson(i)("city").str))
      allStudents += new Student(i, studentsJson(i)("name").str, new Address(addressJson(randomIndex)("country").str, addressJson(randomIndex)("city").str))
    }

    allTeachers.foreach(t => println(s"id ${t.id} ${t.name} lives in ${t.address}"))
    println("-----------------")
    allStudents.foreach(s => println(s"id ${s.id} ${s.name} lives in ${s.address}"))

    val allCourses = ListBuffer[Course]()
    allCourses += new Course(CourseType.JAVA)
    allCourses += new Course(CourseType.OOP)
    allCourses += new Course(CourseType.English)

    val token = new Token(10, "MP")
    val tok = new Token(3, "MP")
    val human = new Human(1, "Masha0", new Address(addressJson(0)("country").str, addressJson(0)("city").str))
    val token1 = human.getToken
    human.setToken(new Token(5, "MP"))
    val token5 = human.getToken
    human.sell(tok)
   
    println(human.getToken.Token_inf())
    println(token)
    Exchange.init(10000, token)
    human.buy(tok)
    //    val teacher_1 = new Teacher(1, "Olena Petrenko", new Address("Ukraine", "Kherson"))
    //    val teacher_2 = new Teacher(2, "Igor Kovalenko", new Address("Ukraine", "Poltava"))
    //
    //    val student1 = new Student(1, "Marina", new Address("Ukraine", "Kyiv"))
    //    val student2 = new Student(2, "Alina", new Address("Ukraine", "Kherson"))
    //

    //
    //
    //    val maxGrade = 5
    //
    //
    //    for (course <- allCourses) {
    //      course.typeCourse match {
    //        case CourseType.JAVA | CourseType.OOP => course.teacher = teacher_1
    //        case CourseType.English => course.teacher = teacher_2
    //      }
    //      course.students += student1
    //      course.students += student2
    //
    //      for (student <- course.students) {
    //        course.generateGrade(student, maxGrade)
    //      }
    //      println(s"\nCourse: ${course.typeCourse}")
    //      for (grade <- course.grades) {
    //        println(s"  Student: ${grade.student.name}, Grade: ${grade.grade}")
    //      }
    //    }
    //
    //
  }
}
