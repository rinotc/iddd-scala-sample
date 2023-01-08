import sbt._

object Dependencies {

  object Google {
    val `guice`                = "com.google.inject"            % "guice"                % "5.1.0"
    val `guice-assistedinject` = "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
  }

  object Logback {
    val `logback-classic` = "ch.qos.logback" % "logback-classic" % "1.4.5"
  }

  object TypeSafe {
    val `config`    = "com.typesafe"       % "config"    % "1.4.2"
    val `play-json` = "com.typesafe.play" %% "play-json" % "2.8.2"
  }

  object TypeLevel {
    val `cats-core` = "org.typelevel" %% "cats-core" % "2.9.0"
  }

  /**
   * @see
   *   [[https://www.scalatest.org/]]
   */
  object ScalaTest {
    val `scalatest`          = "org.scalatest"          %% "scalatest"          % "3.2.14"
    val `scalatestplus-play` = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"
  }

  object CodingWell {
    val `scala-guice` = "net.codingwell" %% "scala-guice" % "5.1.0"
  }

  object Postgresql {
    val `postgresql` = "org.postgresql" % "postgresql" % "42.5.1"
  }

  /**
   * @see
   *   [[http://scalikejdbc.org/]]
   */
  object ScalikeJDBC {

    private val Version = "3.5.0"

    val `scalikejdbc`                  = "org.scalikejdbc" %% "scalikejdbc"                  % Version
    val `scalikejdbc-config`           = "org.scalikejdbc" %% "scalikejdbc-config"           % Version
    val `scalikejdbc-test`             = "org.scalikejdbc" %% "scalikejdbc-test"             % Version
    val `scalikejdbc-play-initializer` = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5"
    val `scalikejdbc-play-dbapi-adapter` =
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
  }
}
