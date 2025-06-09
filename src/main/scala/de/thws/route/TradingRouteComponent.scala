package de.thws
package route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, entity, path, pathPrefix, post}
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Route
import spray.json.*
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.database.TransactionServiceComponent
import de.thws.domain.TradeRequest
import de.thws.json.{TradeRequestJsonFormat, TradeRequestJsonFormatComponent}

trait TradingRouteComponent {
  this: TransactionServiceComponent & TradeRequestJsonFormatComponent =>

  val tradingRoute: TradingRoute
  
  class TradingRoute extends SprayJsonSupport with DefaultJsonProtocol {
    
    def route: Route = pathPrefix("trade") {
     
      post {
        entity(as[TradeRequest]) { req =>
            println(req)
            complete(StatusCodes.OK)
        }
      }
    }
  }
}

