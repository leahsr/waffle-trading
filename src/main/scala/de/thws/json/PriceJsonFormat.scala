package de.thws.json

import de.thws.domain.WafflePrice
import spray.json.{JsNumber, JsObject, JsString, JsValue, RootJsonWriter}

class PriceJsonFormat extends RootJsonWriter[WafflePrice] {

  override def write(obj: WafflePrice): JsValue = JsObject(
    PriceJsonFormat.timestamp -> JsString(obj.timestamp.toString),
    PriceJsonFormat.price -> JsNumber(obj.price.value)
  )
}

object PriceJsonFormat {
  val timestamp = "timestamp"
  val price = "price"
}
