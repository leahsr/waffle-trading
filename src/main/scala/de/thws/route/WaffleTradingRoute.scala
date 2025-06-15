package de.thws
package route

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, extractRequestContext, handleExceptions, mapResponse}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import de.thws.database.TransactionService
import de.thws.service.WaffleTransactionService

class WaffleTradingRoute(
                          transactionService: TransactionService,
                          waffleTransactionService: WaffleTransactionService,
                          marketplaceRoute: PriceRoute,
                          userRoute: UserRoute
                        ) {
  private val exceptionHandler: ExceptionHandler = ExceptionHandler {
    case ex: Exception =>
      complete(HttpResponse(StatusCodes.InternalServerError, entity = s"An error occurred: ${ex.getMessage}"))
  }

  def route: Route = {
    handleExceptions(exceptionHandler) {
      logRequestResponse {
        marketplaceRoute.routes ~ userRoute.route
      }
    }
  }

  // CHAD GPT
  private def logRequestResponse(route: Route): Route = {
    extractRequestContext { requestContext =>
      val request = requestContext.request
      val requestStr = s"Request: ${request.method.value} ${request.uri}"

      mapResponse { response =>
        println(s"$requestStr: ${response.status}")
        response
      } {
        route
      }
    }
  }
}
