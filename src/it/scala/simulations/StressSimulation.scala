package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

/***
 * Push the system beyond its limits to see how it fails.
 * Helps to identify breaking points and bottlenecks.
 */
class StressSimulation extends Simulation {
  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      incrementUsersPerSec(100)
        .times(20)
        .eachLevelLasting(10)
    )
  ).protocols(Utils.baseHttpProtocol)
}
