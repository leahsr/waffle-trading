import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("localhost:8080")
    .acceptHeader("application/json")

  private val scenario1: ScenarioBuilder = scenario("Scenario 1").exec(http("Session").get("/price"))

  private val assertion = global.successfulRequests.count.lt(1)
  setUp(
    scenario1.inject(atOnceUsers(1))
  ).assertions(assertion).protocols(httpProtocol)
}
