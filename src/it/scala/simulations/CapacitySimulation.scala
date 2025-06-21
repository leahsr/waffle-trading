package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

class CapacitySimulation extends Simulation {

  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      incrementUsersPerSec(50)
        .times(20)
        .eachLevelLasting(15)
        .separatedByRampsLasting(5)
    )
  ).protocols(Utils.baseHttpProtocol)
    // this assertion is only for demonstration purposes
    // normally does not make sense for capacity tests
    .assertions(
      global.successfulRequests.percent.gte(99),
      global.responseTime.percentile3.lte(500)
    )
}