package de.thws
package route

import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.domain.WafflePrice
import de.thws.service.{WafflePriceService, WafflePriceUpdateService}

import scala.concurrent.Future

class MarketplaceRoute(
                        wafflePriceUpdateService: WafflePriceUpdateService,
                        wafflePriceService: WafflePriceService
                      ) {

  val routes = pathPrefix("marketplace") {
    path("price") {
      get {
        val price: Future[WafflePrice] = this.wafflePriceUpdateService.currentPrice
        complete(s"Price $price")
      }
    } ~
      path("priceHistory") {
        get {
          val priceHistory: Seq[WafflePrice] = this.wafflePriceService.wafflePriceHistory()
          complete(s"Price History: $priceHistory")
        }
      }
  } ~
    path("stats") {
      get {
        complete("Marketplace stats")
      }
    }
}

