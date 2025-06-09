package de.thws.service

import de.thws.database.TransactionService
import de.thws.domain.WafflePrice
import de.thws.repository.{WafflePriceRepository, WafflePriceRepositoryComponent}

  class WafflePriceService(transactionService: TransactionService, wafflePriceRepository: WafflePriceRepository) {

    def add(wafflePrice: WafflePrice): Unit = {
      transactionService.executeWithoutRetry { transaction =>
        wafflePriceRepository.add(wafflePrice, transaction)
      }
    }

    def wafflePriceHistory(): Seq[WafflePrice] = {
      transactionService.executeWithoutRetry { transaction =>
        wafflePriceRepository.wafflePriceHistory(transaction)
      }
    }
  }


