package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

class SLAMaxUsers extends Simulation {

  val slaThresholdMs = 500

  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      incrementUsersPerSec(50) // Start low
        .times(20)              // Up to 1000 users/sec
        .eachLevelLasting(15)   // 15s per level
        .separatedByRampsLasting(5)
    )
  ).protocols(Utils.baseHttpProtocol)
    .assertions(
      global.successfulRequests.percent.gte(99),
      global.responseTime.percentile3.lte(slaThresholdMs)
    )
}