import endpoints.APIEndpoints
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  private val scenario1: ScenarioBuilder = scenario("Scenario 1").exec(http("Session").get("/price"))
  private val scenario2: ScenarioBuilder = scenario("Scenario 2").exec(APIEndpoints.price)
  private val sellScenario: ScenarioBuilder = scenario("Sell Request")
    .feed(APIEndpoints.userNameFeeder)
    .exec(APIEndpoints.sellRequest)

  private val assertion = global.successfulRequests.count.is(1)
  //  setUp(
  //    sellScenario.inject(atOnceUsers(4))
  //  ).assertions(assertion).protocols(httpProtocol)

  setUp(
    scenario1.inject(rampUsersPerSec(0).to(10000).during(15))
  ).protocols(httpProtocol)
}
