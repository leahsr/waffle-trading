package de.thws
package database

import java.sql.Connection

class TransactionService(jdbcConnections: JdbcConnections) {

  def executeWithoutRetry[R](code: Connection => R): R = {

    val borrowedTransaction = jdbcConnections.transaction()

    try {
      val result = code(borrowedTransaction)
      borrowedTransaction.commit()
      result
    } catch {
      case exception: Exception =>
        borrowedTransaction.rollback()
        throw exception
    } finally {
      borrowedTransaction.close()
    }
  }
}


