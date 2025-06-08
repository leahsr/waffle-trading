package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import de.thws.database.{JdbcConnections, Migration, TransactionService}
import de.thws.route.WaffleTradingRoute

import scala.util.Properties

object Boot extends App {

  implicit val system: ActorSystem = ActorSystem("waffle-trading")

  val user = Properties.envOrElse("WAFFLE_USER", "waffle")
  val password = Properties.envOrElse("WAFFLE_PASSWORD", "password")
  val url = Properties.envOrElse("WAFFLE_DB_URL", "jdbc:postgresql://localhost:5432/waffle")

  val jdbcTransaction = JdbcConnections(url, user, password)
  val transactionService = TransactionService(jdbcTransaction)

  new Migration(transactionService).perform()

  val server = Http().newServerAt("localhost", 8080).bind(new WaffleTradingRoute(transactionService).route)
  println("Server online at http://localhost:8080/\nPress RETURN to stop...")
}
