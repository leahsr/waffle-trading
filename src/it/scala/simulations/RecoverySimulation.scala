package simulations

import io.gatling.core.Predef.*
import scenarios.WaffleScenarios
import utils.Utils

class RecoverySimulation extends Simulation {

  setUp(
    WaffleScenarios.price.inject(
      constantUsersPerSec(5000).during(5),
      constantUsersPerSec(0).during(20),
      constantUsersPerSec(500).during(10)
    )
  ).protocols(Utils.baseHttpProtocol)

}
