package de.thws
package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, handleExceptions, path}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import de.thws.database.TransactionService
import de.thws.service.WaffleTransactionService

class WaffleTradingRoute(
                          transactionService: TransactionService,
                          waffleTransactionService: WaffleTransactionService,
                          marketplaceRoute: PriceRoute,
                          userRoute: UserRoute
                        ) {

  val testRoute: Route = path("test") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Waffel Trading is running</h1>"))
    }
  }

  val exceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: Exception => {

      println(s"Exception in route $e")
      complete(HttpResponse(StatusCodes.BadRequest, entity = e.getMessage))
    }
  }

  def route: Route = handleExceptions(exceptionHandler) {
    testRoute ~ marketplaceRoute.routes ~ userRoute.route
  }
}
