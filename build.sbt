import sbtassembly.AssemblyKeys.assembly

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "waffle-trading"
  )

lazy val IntegrationTest = config("it")

ThisBuild / assemblyMergeStrategy  := {
  case PathList("module-info.class") => MergeStrategy.discard
  case x if x.endsWith("/module-info.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

enablePlugins(GatlingPlugin)

dependencyOverrides += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.7",
  "com.zaxxer" % "HikariCP" % "6.3.0",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.5.18",
  "ch.qos.logback" % "logback-core" % "1.5.18",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
)

val http4sVersion = "0.23.30"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.14.14",
  "io.circe" %% "circe-parser" % "0.14.14",
  "io.circe" %% "circe-generic-extras" % "0.14.5-RC1",
)

libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.14.3" % "it" exclude("com.typesafe.scala-logging", "scala-logging_2.13"),
  "io.gatling" % "gatling-test-framework" % "3.14.3" % "it" exclude("com.typesafe.scala-logging", "scala-logging_2.13"),
)
