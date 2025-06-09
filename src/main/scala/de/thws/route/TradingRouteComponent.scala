package de.thws
package route

import akka.http.scaladsl.server.Directives.{as, entity, path, pathPrefix, post}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json._

import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.database.TransactionServiceComponent
import de.thws.domain.TradeRequest
import de.thws.json.{TradeRequestJsonFormat, TradeRequestJsonFormatComponent}

trait TradingRouteComponent {
  this: TransactionServiceComponent & TradeRequestJsonFormatComponent =>

  val tradingRoute: TradingRoute
  implicit val jsonFormat: TradeRequestJsonFormat = tradeRequestJsonFormat

  class TradingRoute {

    def route: Route = pathPrefix("trade") {
     
      post {
        entity(as[TradeRequest]) { req =>
          globalVolume += req.amount
          complete(StatusCodes.OK)
        }
      }
    }
  }
}

