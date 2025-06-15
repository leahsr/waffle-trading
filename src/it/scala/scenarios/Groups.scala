package scenarios

import endpoints.APIEndpoints
import io.gatling.core.Predef.*
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef.*
import utils.Feeder

import scala.concurrent.duration.DurationInt

object Groups {

  val manualUser: ChainBuilder = group("Manual User")(
    APIEndpoints.priceHistory,
    pause(4, 8),
    APIEndpoints.price,
    pause(2, 4),
    feed(Feeder.userNameFeeder.random),
    feed(Feeder.tradeRequestFeeder),
    APIEndpoints.tradeRequest
  )

  val botUser: ChainBuilder = group("Bot User")(
    APIEndpoints.price,
    pause(100.millis, 200.millis),
    feed(Feeder.botNameFeeder.random),
    feed(Feeder.tradeRequestFeederBots),
    APIEndpoints.tradeRequest
  )

}
