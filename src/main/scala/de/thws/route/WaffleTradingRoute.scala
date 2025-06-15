package de.thws
package route

import cats.effect.IO
import cats.implicits.toSemigroupKOps
import de.thws.database.TransactionService
import de.thws.service.WaffleTransactionService
import org.http4s.HttpRoutes

class WaffleTradingRoute(
                          transactionService: TransactionService,
                          waffleTransactionService: WaffleTransactionService,
                          marketplaceRoute: PriceRoute,
                          userRoute: UserRoute
                        ) {

  val route: HttpRoutes[IO] = marketplaceRoute.route <+> userRoute.route
}
