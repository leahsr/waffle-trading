package de.thws
package route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.domain.{WafflePrice, WafflePriceHistory}
import de.thws.json.{PriceHistoryJsonFormat, PriceJsonFormat}
import de.thws.service.{WafflePriceService, WafflePriceUpdateService}

class PriceRoute(
                  wafflePriceService: WafflePriceService,
                  wafflePriceUpdateService: WafflePriceUpdateService,
                  priceJsonFormat: PriceJsonFormat,
                  priceHistoryJsonFormat: PriceHistoryJsonFormat
                ) extends SprayJsonSupport {

  def routes: Route = {

    path("price") {
      get {
        val price: WafflePrice = wafflePriceUpdateService.currentPrice
        complete(priceJsonFormat.write(price))
      }
    } ~ path("priceHistory") {
      get {
        val priceHistory: WafflePriceHistory = wafflePriceService.wafflePriceHistory()
        complete(priceHistoryJsonFormat.write(priceHistory))
      }
    }
  }
} 




