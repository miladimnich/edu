import sbt.Keys.libraryDependencies
import sbt.*


ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "TokenProject",
    libraryDependencies += "com.lihaoyi" %% "ujson" % "4.4.0"
  )
