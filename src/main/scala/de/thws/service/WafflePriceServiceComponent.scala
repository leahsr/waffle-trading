package de.thws.service

import de.thws.database.TransactionServiceComponent
import de.thws.domain.WafflePrice
import de.thws.repository.WafflePriceRepositoryComponent

trait WafflePriceServiceComponent {
  this: TransactionServiceComponent & WafflePriceRepositoryComponent =>

  val wafflePriceService: WafflePriceService

  class WafflePriceService {

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
}


