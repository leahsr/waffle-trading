package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import de.thws.database.{JdbcConnections, Migration, TransactionService}
import de.thws.json.TradeRequestJsonFormat
import de.thws.repository.{WafflePriceRepository, WaffleTransactionsRepository}
import de.thws.route.{MarketplaceRoute, TradingRoute, WaffleTradingRoute}
import de.thws.service.{WafflePriceService, WafflePriceUpdateService, WaffleTransactionService}

import scala.util.Properties

object Boot extends App {
  implicit val system: ActorSystem = ActorSystem("waffle-trading")

  val databaseConfiguration = DatabaseConfiguration(
    user = Properties.envOrElse("WAFFLE_USER", "waffle"),
    password = Properties.envOrElse("WAFFLE_PASSWORD", "password"),
    jdbc_url = Properties.envOrElse("WAFFLE_DB_URL", "jdbc:postgresql://localhost:5432/waffle"),
  )
  val jdbcConnections: JdbcConnections = new JdbcConnections(databaseConfiguration)

  val transactionService = new TransactionService(jdbcConnections)
  val migration = new Migration(transactionService)
  migration.perform()

  val waffleTransactionRepository = new WaffleTransactionsRepository()
  val wafflePriceRepository = new WafflePriceRepository()

  val waffleTransactionService = new WaffleTransactionService(waffleTransactionRepository, transactionService)
  val wafflePriceService = new WafflePriceService(transactionService, wafflePriceRepository)
  val wafflePriceUpdateService = new WafflePriceUpdateService(wafflePriceService)

  val tradeRequestJsonFormat = TradeRequestJsonFormat()


  val marketplaceRoute = new MarketplaceRoute(wafflePriceService, wafflePriceUpdateService)
  val tradingRoute = new TradingRoute(transactionService, tradeRequestJsonFormat)
  val waffleTradingRoute = new WaffleTradingRoute(transactionService, waffleTransactionService, marketplaceRoute, tradingRoute)

  println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  val server = Http()
    .newServerAt("localhost", 8080)
    .bind(waffleTradingRoute.route)
}
