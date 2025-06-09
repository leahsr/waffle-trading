package de.thws.database

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import de.thws.DatabaseConfiguration

import java.sql.Connection


class JdbcConnections(databaseConfiguration: DatabaseConfiguration) {


  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(databaseConfiguration.jdbc_url)
  hikariConfig.setUsername(databaseConfiguration.user)
  hikariConfig.setPassword(databaseConfiguration.password)
  hikariConfig.setMaximumPoolSize(10)
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



