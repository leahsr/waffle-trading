package de.thws.json

import de.thws.domain.WafflePrice
import io.circe.*

object PriceJsonFormat {
  val timestamp = "timestamp"
  val price = "price"

  given Encoder[WafflePrice] = (a: WafflePrice) => Json.obj(
    (timestamp, Json.fromString(a.timestamp.toString)),
    (price, Json.fromDouble(a.price.value).get)
  )
}
