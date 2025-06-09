package de.thws.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives.{Segment, path}
import akka.http.scaladsl.server.{Directives, Route}
import de.thws.database.TransactionService
import de.thws.domain.{TradeRequest, UserName}
import de.thws.json.TradeRequestJsonFormat
import de.thws.service.{WafflePriceService, WafflePriceUpdateService, WaffleTransactionService}
import spray.json.DefaultJsonProtocol

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class UserRoute(
                 val waffleTransactionService: WaffleTransactionService,
                 val priceUpdateService: WafflePriceUpdateService,
                 implicit val tradeRequestJsonFormat: TradeRequestJsonFormat
               ) extends Directives, SprayJsonSupport, DefaultJsonProtocol {


  val route: Route = path("user" / Segment) { encodedUserName =>
    val userName = UserName(URLEncoder.encode(encodedUserName, StandardCharsets.UTF_8.toString))

    path("trade") {
      post {
        entity(as[TradeRequest]) { tradeRequest =>
          val tradeCommand = tradeRequest.toCommand(userName)
          val price = priceUpdateService.currentPrice
          waffleTransactionService
            .add(tradeCommand, price)
            .map(r => complete(s"$r"))
            .getOrElse(complete(StatusCodes.InternalServerError))
        }
      }
    }
  }
}
