package de.thws


case class DatabaseConfiguration(
                                  user: String,
                                  password: String,
                                  jdbc_url: String,
                                  connectionPoolSize: Int
                                )
