package de.thws
package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.{Directive, Route}
import de.thws.database.TransactionServiceComponent
import de.thws.service.WaffleTransactionServiceComponent

trait WaffleTradingRouteComponent {
  this: TransactionServiceComponent
    & WaffleTransactionServiceComponent
    & MarketplaceRouteComponent
    & TradingRouteComponent
  =>

  val waffleTradingRoute: WaffleTradingRoute

  class WaffleTradingRoute {

    val testRoute: Route = path("test") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Waffel Trading is running</h1>"))
      }
    }

    def route: Route = testRoute ~ marketplaceRoute.routes ~ tradingRoute.route
  }
}
