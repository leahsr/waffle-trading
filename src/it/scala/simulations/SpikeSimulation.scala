package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

/***
 * Test how the system handles sudden large spikes in traffic.
 * Simulates real-world events (e.g., market open) where many users hit the system simultaneously.
 * It checks autoscaling or crash resilience.
 */
class SpikeSimulation extends Simulation {
  setUp(
    WaffleScenarios.standardTrafficScenario.inject(
      nothingFor(5),
      atOnceUsers(500)
    )
  ).protocols(Utils.baseHttpProtocol)
}
