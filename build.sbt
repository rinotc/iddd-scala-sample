import Dependencies._

ThisBuild / scalaVersion := "2.13.10"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val ScalacOptions = Seq(
  "-deprecation",
  "-feature",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ypartial-unification"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(common % "compile->compile;test->test", agilepm, collaboration, identityaccess)
  .aggregate(common, agilepm, collaboration, identityaccess)
  .settings(
    name := """iddd-scala-sample""",
    libraryDependencies ++= Seq(
      guice,
      Google.`guice`,
      Google.`guice-assistedinject`,
      ScalaTest.`scalatestplus-play` % Test,
      CodingWell.`scala-guice`
    )
  )

lazy val common = (project in file("common"))
  .settings(
    name          := "common",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      TypeLevel.`cats-core`,
      TypeSafe.`play-json`,
      Google.`guice`,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-config`,
      ScalikeJDBC.`scalikejdbc-test` % Test
    )
  )

lazy val agilepm = (project in file("agilepm"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    name          := "agilepm",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq()
  )

lazy val collaboration = (project in file("collaboration"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    name          := "collaboration",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq()
  )

lazy val identityaccess = (project in file("identityaccess"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    name          := "identityaccess",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq()
  )
