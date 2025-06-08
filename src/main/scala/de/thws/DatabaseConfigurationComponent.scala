package de.thws

trait DatabaseConfigurationComponent {
  val databaseConfiguration: DatabaseConfiguration

  case class DatabaseConfiguration(
                                    user: String,
                                    password: String,
                                    jdbc_url: String,
                                  )
}