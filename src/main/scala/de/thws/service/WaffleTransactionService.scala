package de.thws.service

import de.thws.database.TransactionService
import de.thws.domain.{UserName, WafflePrice, WaffleTransaction, WaffleTransactionCommand, WaffleTransactions}
import de.thws.repository.WaffleTransactionsRepository


class WaffleTransactionService(
                                waffleTransactionsRepository: WaffleTransactionsRepository,
                                transactionService: TransactionService
                              ) {
  def add(waffleTransaction: WaffleTransactionCommand, wafflePrice: WafflePrice): Unit = {
    transactionService.executeWithoutRetry { transaction =>
      waffleTransactionsRepository.add(waffleTransaction, wafflePrice, transaction)
    }
  }

  def waffleTransactions(userName: UserName): WaffleTransactions = {
    transactionService.executeWithoutRetry { transaction =>
      waffleTransactionsRepository.waffleTransactions(userName, transaction)
    }
  }
}


