package endpoints

import de.thws.json.TradeRequestJsonFormat
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.request.builder.HttpRequestBuilder

object APIEndpoints {

  val price: HttpRequestBuilder = http("price")
    .get("/price")
    .check(status.is(200))
  
  val priceHistory: HttpRequestBuilder = http("Price History")
    .get("/priceHistory")
    .check(status.is(200))

  val tradeRequest: HttpRequestBuilder = http("Trade")
    .post("/user/#{name}/trade")
    .asJson
    .body(StringBody(
      s"""{
         |  "${TradeRequestJsonFormat.quantity}": #{quantity},
         |  "${TradeRequestJsonFormat.transactionType}": "#{transactionType}"
         |}
         |""".stripMargin))
    .check(status.is(201))
    .check(jmesPath("quantity").is("#{quantity}"))
}
