package de.thws.database

import java.sql.{Connection, PreparedStatement, ResultSet}


class TransactionUtils

object TransactionUtils {
  def execute(sql: String, transaction: Connection)
             (prepareImpl: PreparedStatement => Unit): Unit = {
    prepare(sql, transaction, prepareImpl).execute()
  }

  def executeQuery[R](sql: String, transaction: Connection)
                     (
                       prepareImpl: PreparedStatement => Unit,
                       mapResultSet: ResultSet => R,
                     ): R = {
    val resultSet = prepare(sql, transaction, prepareImpl).executeQuery()
    mapResultSet(resultSet)
  }

  private def prepare(
                       sql: String,
                       transaction: Connection,
                       prepareFnc: PreparedStatement => Unit
                     ): PreparedStatement = {
    val prepareStatement = transaction.prepareStatement(sql)
    prepareFnc(prepareStatement)
    prepareStatement
  }
}

