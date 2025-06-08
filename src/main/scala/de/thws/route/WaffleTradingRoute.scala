package de.thws
package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directive, Route}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.database.TransactionService
import de.thws.service.{WafflePriceService, WafflePriceUpdateService, WaffleTransactionService}

class WaffleTradingRoute(
                        transactionService: TransactionService
                        ) {

  val wafflePriceService = new WafflePriceService(transactionService)
  val wafflePriceUpdateService = new WafflePriceUpdateService(wafflePriceService = wafflePriceService)
  val waffleTransactionService = new WaffleTransactionService(transactionService)

  val testRoute = path("test") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Waffel Trading is running</h1>"))
    }
  }
  val marketplaceRoute = new MarketplaceRoute(wafflePriceUpdateService, wafflePriceService)
  val tradingRoute = new TradingRoute(waffleTransactionService)

  val route: Route = testRoute ~ marketplaceRoute.routes ~ tradingRoute.route
}
