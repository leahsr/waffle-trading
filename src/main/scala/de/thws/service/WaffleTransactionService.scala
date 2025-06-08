package de.thws.service

import de.thws.database.TransactionService
import de.thws.domain.{UserName, WaffleTransaction, WaffleTransactions}
import de.thws.repository.WaffleTransactionsRepository

class WaffleTransactionService(transactionService: TransactionService) {
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

  // TODO: inject?
  private def waffleTransactionsRepository = WaffleTransactionsRepository.build()
}
