package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

// Generally used to monitor the application performance with a fixed load over a long period of time.
// Useful for checking memory leaks and database degradation over time.
class SoakSimulation extends Simulation {
  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      constantConcurrentUsers(100).during(60)
    ).protocols(Utils.baseHttpProtocol)
  )

  //  private val assertion = global.successfulRequests.count.is(1)
}
