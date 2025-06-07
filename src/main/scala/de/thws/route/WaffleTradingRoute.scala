package de.thws
package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directive
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.service.WafflePriceService

class WaffleTradingRoute {

  val testRoute = path("test") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Waffel Trading is running</h1>"))
    }
  }
  
  val marketplaceRoute = new MarketplaceRoute(new WafflePriceService())
  val tradingRoute = new TradingRoute()

  val route = testRoute ~ marketplaceRoute.routes ~ tradingRoute.route
}
