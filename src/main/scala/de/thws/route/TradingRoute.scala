package de.thws
package route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, entity, pathPrefix, post}
import akka.http.scaladsl.server.Route
import de.thws.database.TransactionService
import de.thws.domain.TradeRequest
import de.thws.json.TradeRequestJsonFormat
import spray.json.*


class TradingRoute(
                    transactionService: TransactionService,
                    implicit val tradeRequestJsonFormat: TradeRequestJsonFormat
                  ) extends SprayJsonSupport with DefaultJsonProtocol {
  def route: Route = pathPrefix("trade") {

    post {
      entity(as[TradeRequest]) { req =>
        println(req)
        complete(StatusCodes.OK)
      }
    }
  }
}

