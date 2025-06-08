package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import de.thws.repository.WaffleTransactionsRepository

import scala.util.Properties

object Boot extends App, Components {
  implicit val system: ActorSystem = ActorSystem("waffle-trading")

  override val databaseConfiguration: Boot.DatabaseConfiguration = DatabaseConfiguration(
    user = Properties.envOrElse("WAFFLE_USER", "waffle"),
    password = Properties.envOrElse("WAFFLE_PASSWORD", "password"),
    jdbc_url = Properties.envOrElse("WAFFLE_DB_URL", "jdbc:postgresql://localhost:5432/waffle"),
  )
  override val migration: Boot.Migration = new Migration
  override val jdbcConnections: Boot.JdbcConnections = new JdbcConnections

  override val transactionService: Boot.TransactionService = new TransactionService
  override val waffleTransactionService: Boot.WaffleTransactionService = new WaffleTransactionService
  override val wafflePriceService: Boot.WafflePriceService = new WafflePriceService
  override val wafflePriceUpdateService: Boot.WafflePriceUpdateService = new WafflePriceUpdateService

  override val waffleTransactionsRepository: WaffleTransactionsRepository = new WaffleTransactionsRepository

  override val waffleTradingRoute: Boot.WaffleTradingRoute = new WaffleTradingRoute
  override val marketplaceRoute: Boot.MarketplaceRoute = new MarketplaceRoute
  override val tradingRoute: Boot.TradingRoute = new TradingRoute


  println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  val server = Http()
    .newServerAt("localhost", 8080)
    .bind(waffleTradingRoute.route)
}
