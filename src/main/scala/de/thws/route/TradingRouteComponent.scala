package de.thws
package route

import akka.http.scaladsl.server.Directives.{path, pathPrefix, post}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.database.TransactionServiceComponent

trait TradingRouteComponent {
  this: TransactionServiceComponent =>

  val tradingRoute: TradingRoute
  class TradingRoute {

    def route: Route = pathPrefix("trade") {
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
}
