package de.thws
package database

import de.thws.database.JdbcConnectionsComponent

import java.sql.Connection

trait TransactionServiceComponent {
  this: JdbcConnectionsComponent =>

  val transactionService: TransactionService

  class TransactionService {

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
}


