package simulations

import io.gatling.core.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.WaffleScenarios
import utils.Utils

class SpikeSimulation extends Simulation {
  setUp(
    WaffleScenarios.priceHistory.inject(
      nothingFor(5),
      atOnceUsers(5000)
    )
  ).protocols(Utils.baseHttpProtocol)
}
