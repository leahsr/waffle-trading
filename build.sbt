ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "waffle-trading"
  )

lazy val IntegrationTest = config("it")

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "org.postgresql" % "postgresql" % "42.7.6",
  "com.zaxxer" % "HikariCP" % "6.3.0",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.14.3" % "it",
  "io.gatling"            % "gatling-test-framework"    % "3.14.3" % "it",
)