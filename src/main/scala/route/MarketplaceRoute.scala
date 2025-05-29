package de.thws
package route

import service.WafflePriceService

import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.domain.WafflePrice

import scala.concurrent.Future

class MarketplaceRoute(wafflePriceService: WafflePriceService) {

  val routes = pathPrefix("marketplace") {
    path("price") {
      get {
        val price: Future[WafflePrice] = this.wafflePriceService.currentPrice
        complete(s"Price $price")
      }
    } ~
      path("stats") {
        get {
          complete("Marketplace stats")
        }
      }
  }

}
