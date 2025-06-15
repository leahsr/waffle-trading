import de.thws.domain.WaffleTransactionType
import io.gatling.core.Predef.csv
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.Predef.configuration

import scala.util.Random

object Feeder {

  val userNameFeeder: BatchableFeederBuilder[String] = csv("usernames.csv")
  
  val botNameFeeder: BatchableFeederBuilder[String] = csv("bot_username.csv")

  val tradeRequestFeeder: Iterator[Map[String, String]] = Iterator.continually {
    Map(
      "quantity" -> Random.between(1, 100).toString,
      "transactionType" -> {
        if (Random.nextBoolean()) WaffleTransactionType.Sell.value else WaffleTransactionType.Buy.value
      }
    )
  }

}
