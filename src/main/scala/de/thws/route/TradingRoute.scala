package de.thws
package route

import akka.http.scaladsl.server.Directives.{path, pathPrefix, post}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.service.WaffleTransactionService

class TradingRoute(
                  waffleTransactionService: WaffleTransactionService
                  ) {

  val route = pathPrefix("trade") {
    path("buy") {
      post {
        ???
        //        entity(as[BuyRequest]) { req =>
        //          globalVolume += req.amount
        //          complete(StatusCodes.OK)
        //        }
      } ~
      path("sell") {
        post {
          ???
          //          entity(as[SellRequest]) { req =>
          //            globalVolume = math.max(0, globalVolume - req.amount)
          //            complete(StatusCodes.OK)
          //          }
        }
      }
    }
  }
}