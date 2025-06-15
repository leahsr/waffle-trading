package de.thws

import cats.effect.{ExitCode, IO}
import com.comcast.ip4s.Literals.ipv6
import com.comcast.ip4s.{ipv4, ipv6, port}
import de.thws.database.{JdbcConnections, Migration, TransactionService}
import de.thws.repository.{WafflePriceRepository, WaffleTransactionsRepository}
import de.thws.route.{PriceRoute, UserRoute, WaffleTradingRoute}
import de.thws.service.{WafflePriceService, WafflePriceUpdateService, WaffleTransactionService}
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.server.Router
import org.http4s.server.middleware.Logger

class AppInitializer {

  def start(databaseConfiguration: DatabaseConfiguration): IO[ExitCode] = {

    val jdbcConnections = new JdbcConnections(databaseConfiguration)
    val transactionService = new TransactionService(jdbcConnections)
    val migration = new Migration(transactionService)

    migration.perform()

    val waffleTransactionRepository = new WaffleTransactionsRepository()
    val wafflePriceRepository = new WafflePriceRepository()
    val waffleTransactionService = new WaffleTransactionService(waffleTransactionRepository, transactionService)
    val wafflePriceService = new WafflePriceService(transactionService, wafflePriceRepository)
    val wafflePriceUpdateService = new WafflePriceUpdateService(wafflePriceService)

    val marketplaceRoute = new PriceRoute(wafflePriceService, wafflePriceUpdateService)
    val userRoute = new UserRoute(waffleTransactionService, wafflePriceUpdateService)
    val waffleTradingRoute = new WaffleTradingRoute(transactionService, waffleTransactionService, marketplaceRoute, userRoute)

    val loggedRoute = Logger.httpRoutes(logHeaders = true, logBody = true)(waffleTradingRoute.route)

    val httpApp = Router("/" -> loggedRoute).orNotFound

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withHost(ipv6"::")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
  }
}
