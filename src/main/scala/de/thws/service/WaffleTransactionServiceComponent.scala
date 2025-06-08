package de.thws.service

import de.thws.database.TransactionServiceComponent
import de.thws.domain.{UserName, WaffleTransaction, WaffleTransactions}
import de.thws.repository.WaffleTransactionsRepositoryComponent

trait WaffleTransactionServiceComponent {
  this: WaffleTransactionsRepositoryComponent & TransactionServiceComponent =>

  val waffleTransactionService: WaffleTransactionService

  class WaffleTransactionService {
    def add(waffleTransaction: WaffleTransaction): Unit = {
      transactionService.executeWithoutRetry { transaction =>
        waffleTransactionsRepository.add(waffleTransaction, transaction)
      }
    }

    def waffleTransactions(userName: UserName): WaffleTransactions = {
      transactionService.executeWithoutRetry { transaction =>
        waffleTransactionsRepository.waffleTransactions(userName, transaction)
      }
    }
  }
}


