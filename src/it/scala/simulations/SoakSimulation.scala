package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

import scala.concurrent.duration.DurationInt

class SoakSimulation extends Simulation {
  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      constantConcurrentUsers(1000).during(15.minutes)
    ).protocols(Utils.baseHttpProtocol)
  ).assertions(
    global.successfulRequests.percent.gte(99),
    global.responseTime.percentile3.lte(200)
  )
}
