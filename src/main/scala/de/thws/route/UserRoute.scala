package de.thws.route

import cats.effect.IO
import de.thws.domain.{TradeRequest, UserName}
import de.thws.json.TradeRequestJsonFormat.given
import de.thws.json.TransactionJsonFormat.given
import de.thws.service.{WafflePriceUpdateService, WaffleTransactionService}
import io.circe.syntax.*
import org.http4s.*
import org.http4s.Method.*
import org.http4s.circe.*
import org.http4s.dsl.io.*

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class UserRoute(
                 val waffleTransactionService: WaffleTransactionService,
                 val priceUpdateService: WafflePriceUpdateService,
               ) {

  val route: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req@POST -> Root / "user" / encodedUserName / "trade" =>
      val userName = UserName(URLDecoder.decode(encodedUserName, StandardCharsets.UTF_8.toString))

      req.as[TradeRequest].attempt.flatMap {
        case Left(ioError) =>
          BadRequest(ioError.getMessage)
        case Right(tradeRequest) =>
          val tradeCommand = tradeRequest.toCommand(userName)
          val price = priceUpdateService.currentPrice
          val transaction = waffleTransactionService.add(tradeCommand, price).get

          Created(transaction.asJson)
      }
  }
}
