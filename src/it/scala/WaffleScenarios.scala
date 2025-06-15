import endpoints.APIEndpoints
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder

object WaffleScenarios {

  val price: ScenarioBuilder = scenario("Price")
    .exec(APIEndpoints.price)

  val priceHistory: ScenarioBuilder = scenario("Price History")
    .exec(APIEndpoints.priceHistory)

  val tradeScenario: ScenarioBuilder = scenario("Trade Request")
    .feed(Feeder.userNameFeeder.random)
    .feed(Feeder.tradeRequestFeeder)
    .exec(APIEndpoints.tradeRequest)
  
  val standardTrafficScenario: ScenarioBuilder = scenario("Standard Traffic")
    .feed(Feeder.userNameFeeder.random)
}
