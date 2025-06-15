import endpoints.APIEndpoints
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

 
  

  private val assertion = global.successfulRequests.count.is(1)
  //  setUp(
  //    sellScenario.inject(atOnceUsers(4))
  //  ).assertions(assertion).protocols(httpProtocol)

  setUp(
    WaffleScenarios.tradeScenario.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

//  setUp(
//    tradeScenario.inject(rampUsersPerSec(0).to(1000).during(10))
//  ).protocols(httpProtocol)
}
