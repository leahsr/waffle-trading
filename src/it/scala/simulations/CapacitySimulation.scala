package simulations

import io.gatling.core.Predef.*
import scenarios.WaffleScenarios
import utils.Utils

// Generally used to determine the maximum number of virtual users your application can sustain.
// Users arrival rate gets incremented over multiple levels, and we analyze the metrics
// (response time, error ratio..etc) at each level according to our benchmarks.
class CapacitySimulation extends Simulation {
  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      incrementUsersPerSec(50)
        .times(10)
        .eachLevelLasting(10)
        .separatedByRampsLasting(4)
    )
  ).protocols(Utils.baseHttpProtocol)
}
