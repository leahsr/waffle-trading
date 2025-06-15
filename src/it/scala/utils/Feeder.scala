package utils

import de.thws.domain.WaffleTransactionType
import io.gatling.core.Predef.{configuration, csv}
import io.gatling.core.feeder.BatchableFeederBuilder

import scala.util.Random

object Feeder {

  val userNameFeeder: BatchableFeederBuilder[String] = csv("usernames.csv")
  
  val botNameFeeder: BatchableFeederBuilder[String] = csv("bot_usernames.csv")

  val tradeRequestFeeder: Iterator[Map[String, String]] = Iterator.continually {
    Map(
      "quantity" -> Random.between(1, 100).toString,
      "transactionType" -> {
        if (Random.nextBoolean()) WaffleTransactionType.Sell.value else WaffleTransactionType.Buy.value
      }
    )
  }

  val tradeRequestFeederBots: Iterator[Map[String, String]] = Iterator.continually {
    Map(
      "quantity" -> Random.between(1, 25).toString,
      "transactionType" -> {
        if (Random.nextBoolean()) WaffleTransactionType.Sell.value else WaffleTransactionType.Buy.value
      }
    )
  }

}
