package de.thws.json

import de.thws.domain.WafflePriceHistory
import de.thws.json.PriceJsonFormat.given
import io.circe.*
import io.circe.syntax.*


object PriceHistoryJsonFormat {

  given Encoder[WafflePriceHistory] = (a: WafflePriceHistory) => Json.arr(
    a.values.map(_.asJson) *
  )
}


