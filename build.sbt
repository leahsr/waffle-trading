ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "waffle-trading"
  )

lazy val IntegrationTest = config("it")

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.6",
  "com.zaxxer" % "HikariCP" % "6.3.0",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.5.18",
  "ch.qos.logback" % "logback-core" % "1.5.18",
)

//libraryDependencies ++= Seq(
//  "org.http4s" %% "http4s-ember-client" % "0.23.30",
//  "org.http4s" %% "http4s-ember-server" % "0.23.30",
//  "org.http4s" %% "http4s-dsl"          % "0.23.30",
//)

libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.14.3" % "it",
  "io.gatling" % "gatling-test-framework" % "3.14.3" % "it",
)
