package de.thws

import cats.effect.{ExitCode, IO, IOApp}

import scala.util.Properties

object Boot extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
      val databaseConfiguration = DatabaseConfiguration(
        user = Properties.envOrElse("WAFFLE_USER", "waffle"),
        password = Properties.envOrElse("WAFFLE_PASSWORD", "password"),
        jdbc_url = Properties.envOrElse("WAFFLE_DB_URL", "jdbc:postgresql://localhost:5432/waffle"),
        connectionPoolSize = 20
      )
      AppInitializer().start(databaseConfiguration: DatabaseConfiguration)
  }

}
