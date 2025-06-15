package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import de.thws.database.{JdbcConnections, Migration, TransactionService}
import de.thws.json.{PriceHistoryJsonFormat, PriceJsonFormat, TradeRequestJsonFormat, TransactionJsonFormat}
import de.thws.repository.{WafflePriceRepository, WaffleTransactionsRepository}
import de.thws.route.{PriceRoute, UserRoute, WaffleTradingRoute}
import de.thws.service.{WafflePriceService, WafflePriceUpdateService, WaffleTransactionService}

class AppInitializer {
  
  private val config = ConfigFactory.load("akka.conf")
  implicit val system: ActorSystem = ActorSystem("waffle-trading")

  def start(databaseConfiguration: DatabaseConfiguration): Unit = {
    
    val jdbcConnections = new JdbcConnections(databaseConfiguration)
    val transactionService = new TransactionService(jdbcConnections)
    val migration = new Migration(transactionService)

    migration.perform()

    val waffleTransactionRepository = new WaffleTransactionsRepository()
    val wafflePriceRepository = new WafflePriceRepository()
    val waffleTransactionService = new WaffleTransactionService(waffleTransactionRepository, transactionService)
    val wafflePriceService = new WafflePriceService(transactionService, wafflePriceRepository)
    val wafflePriceUpdateService = new WafflePriceUpdateService(wafflePriceService)

    val tradeRequestJsonFormat = TradeRequestJsonFormat()
    val transactionJsonFormat = TransactionJsonFormat()
    val priceJsonFormat = PriceJsonFormat()
    val priceHistoryJsonFormat = PriceHistoryJsonFormat(priceJsonFormat)

    val marketplaceRoute = new PriceRoute(wafflePriceService, wafflePriceUpdateService, priceJsonFormat, priceHistoryJsonFormat)
    val userRoute = new UserRoute(waffleTransactionService, wafflePriceUpdateService, tradeRequestJsonFormat, transactionJsonFormat)
    val waffleTradingRoute = new WaffleTradingRoute(transactionService, waffleTransactionService, marketplaceRoute, userRoute)

    println("Server online at http://localhost:8080/")
    Http().newServerAt("localhost", 8080).bind(waffleTradingRoute.route)
  }
}
