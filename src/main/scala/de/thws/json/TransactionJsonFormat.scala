package de.thws.json

import cats.effect.IO
import de.thws.domain.*
import io.circe.*
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}

import java.time.Instant

object TransactionJsonFormat {
  val id = "id"
  val timestamp = "timestamp"
  val transactionType = "transactionType"
  val price = "price"
  val quantity = "quantity"
  val userName = "userName"

  given Encoder[WaffleTransaction] = (a: WaffleTransaction) => Json.obj(
    (id, Json.fromInt(a.id.value)),
    (timestamp, Json.fromString(a.timestamp.toString)),
    (transactionType, Json.fromString(a.transactionType.value)),
    (price, Json.fromDouble(a.price.value).get),
    (quantity, Json.fromInt(a.quantity.value)),
    (userName, Json.fromString(a.userName.value))
  )

  given Decoder[WaffleTransaction] = (c: HCursor) => for {
    id <- c.downField(id).as[Int].map(TransactionId.apply)
    timestamp <- c.downField(timestamp).as[Instant]
    transactionType <- c.downField(transactionType).as[String].map(WaffleTransactionType.apply)
    price <- c.downField(price).as[Double].map(Price.apply)
    quantity <- c.downField(quantity).as[Int].map(Quantity.apply)
    userName <- c.downField(userName).as[String].map(UserName.apply)
  } yield WaffleTransaction(id, timestamp, transactionType, price, quantity, userName)

  given EntityDecoder[IO, WaffleTransaction] = jsonOf[IO, WaffleTransaction]
  given EntityEncoder[IO, WaffleTransaction] = jsonEncoderOf[IO, WaffleTransaction]
}
