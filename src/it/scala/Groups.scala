import endpoints.APIEndpoints
import io.gatling.core.Predef.*
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

object Groups {

  val manualUser: ChainBuilder = group("Manual User")(
    APIEndpoints.priceHistory,
    pause(5, 15),
    APIEndpoints.price,
    pause(2, 10),
    feed(Feeder.userNameFeeder.random),
    feed(Feeder.tradeRequestFeeder),
    APIEndpoints.tradeRequest
  )

  val botUser: ChainBuilder = group("Bot User")(
    APIEndpoints.price,
    pause(100.millis, 200.millis),
    feed(Feeder.botNameFeeder.random),
    APIEndpoints.tradeRequest
  )

}
