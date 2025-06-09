package de.thws.json

import de.thws.domain.WafflePriceHistory
import spray.json.{JsArray, JsNumber, JsObject, JsString, JsValue, RootJsonWriter}

class PriceHistoryJsonFormat(
                              priceJsonFormat: PriceJsonFormat
                            ) extends RootJsonWriter[WafflePriceHistory] {

  override def write(obj: WafflePriceHistory): JsValue = JsArray(
    obj.values.map(wafflePrice => priceJsonFormat.write(wafflePrice)): _*
  )
}


