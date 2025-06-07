package de.thws
package database

import java.sql.Connection

class TransactionService(transactionServiceJdbc: JdbcConnections) {

  def executeWithoutRetry[R](code: Connection => R): R = {

    val borrowedTransaction = this.transactionServiceJdbc.transaction()

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
