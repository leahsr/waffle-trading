package de.thws.json

import cats.effect.IO
import de.thws.domain.{Quantity, TradeRequest, WaffleTransactionType}
import io.circe.*
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

object TradeRequestJsonFormat {
  val quantity = "quantity"
  val transactionType = "transactionType"

  given Decoder[TradeRequest] = (c: HCursor) => for {
    quantity <- c.downField(quantity).as[Int].map(Quantity.apply)
    transactionType <- c.downField(transactionType).as[String].map(WaffleTransactionType.apply)
  } yield TradeRequest(quantity, transactionType)

  given EntityDecoder[IO, TradeRequest] = jsonOf[IO, TradeRequest]
}