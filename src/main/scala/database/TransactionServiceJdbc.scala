package de.thws
package database

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import java.sql.Connection

class TransactionServiceJdbc(
                              jdbcUrl: String,
                              user: String,
                              password: String,
                              numberOfConnections: Int = 10
                            ) {

  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(jdbcUrl)
  hikariConfig.setUsername(user)
  hikariConfig.setPassword(password)
  hikariConfig.setMaximumPoolSize(numberOfConnections)
  hikariConfig.setConnectionTimeout(1000 * 60)
  hikariConfig.addHealthCheckProperty("connectivityCheckTimeoutMs", "1000")
  hikariConfig.setPoolName("jdbc-transaction-service")
  hikariConfig.setAutoCommit(false)

  private val hikariDataSource = new HikariDataSource(hikariConfig)

  def transaction(): Connection = {

    this.hikariDataSource.getConnection
  }

  def close(): Unit = hikariDataSource.close()
}

