package de.thws.service

import de.thws.database.TransactionService
import de.thws.domain.{WafflePrice, WafflePriceHistory}
import de.thws.repository.WafflePriceRepository

class WafflePriceService(transactionService: TransactionService, wafflePriceRepository: WafflePriceRepository) {

  def add(wafflePrice: WafflePrice): Unit = {
    transactionService.executeWithoutRetry { transaction =>
      wafflePriceRepository.add(wafflePrice, transaction)
    }
  }

  def wafflePriceHistory(): WafflePriceHistory = {
    transactionService.executeWithoutRetry { transaction =>
      wafflePriceRepository.wafflePriceHistory(transaction)
    }
  }
}


