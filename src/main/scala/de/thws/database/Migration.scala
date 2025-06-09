package de.thws
package database

class Migration(transactionService: TransactionService) {

  def perform(): Unit = {

    val tableExistsSql =
      s"""
         |SELECT COUNT(table_name) AS migration_count
         |FROM INFORMATION_SCHEMA.TABLES
         |WHERE table_name = 'waffle_migration';
         |""".stripMargin

    val migrationScript =
      s"""
         |CREATE TABLE waffle_migration (id VARCHAR(36) PRIMARY KEY);
         |
         |CREATE TYPE TRANSACTION_TYPE AS ENUM ('sell', 'buy');
         |
         |CREATE TABLE transaction (
         |  transaction_id serial PRIMARY KEY,
         |  timestamp TIMESTAMP,
         |  transaction_type TRANSACTION_TYPE,
         |  price DOUBLE PRECISION,
         |  quantity INTEGER,
         |  user_name VARCHAR(36)
         |);
         |
         |CREATE TABLE price (
         |  price_timestamp TIMESTAMP PRIMARY KEY,
         |  price DOUBLE PRECISION
         |);
         |""".stripMargin

    val runMigration = transactionService.executeWithoutRetry { transaction =>

      val resultSet = transaction.prepareStatement(tableExistsSql).executeQuery()

      resultSet.next()
      resultSet.getInt("migration_count") == 0
    }

    if (runMigration) {

      transactionService.executeWithoutRetry { transaction =>
        transaction.prepareStatement(migrationScript).execute()
      }
    }
  }
}

