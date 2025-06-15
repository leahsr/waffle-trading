package de.thws
package route

import cats.effect.*
import de.thws.domain.{WafflePrice, WafflePriceHistory}
import de.thws.json.PriceHistoryJsonFormat.given
import de.thws.json.PriceJsonFormat.given
import de.thws.service.{WafflePriceService, WafflePriceUpdateService}
import io.circe.syntax.*
import org.http4s.*
import org.http4s.Method.*
import org.http4s.circe.*
import org.http4s.dsl.io.*

class PriceRoute(
                  wafflePriceService: WafflePriceService,
                  wafflePriceUpdateService: WafflePriceUpdateService,
                ) {

  val route: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "price" =>
      val price: WafflePrice = wafflePriceUpdateService.currentPrice
      Ok(price.asJson)
    case GET -> Root / "priceHistory" =>
      val priceHistory: WafflePriceHistory = wafflePriceService.wafflePriceHistory()
      Ok(priceHistory.asJson)
  }

} 




