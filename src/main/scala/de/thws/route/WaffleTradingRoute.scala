package de.thws
package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.{Directive, Route}
import de.thws.database.TransactionService
import de.thws.service.WaffleTransactionService

class WaffleTradingRoute(
                          transactionService: TransactionService,
                          waffleTransactionService: WaffleTransactionService,
                          marketplaceRoute: MarketplaceRoute,
                          tradingRoute: TradingRoute
                        ) {

  val testRoute: Route = path("test") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Waffel Trading is running</h1>"))
    }
  }

  def route: Route = testRoute ~ marketplaceRoute.routes ~ tradingRoute.route
}
