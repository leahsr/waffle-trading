package endpoints

import io.gatling.core.Predef.*
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef.*
import io.gatling.http.request.builder.HttpRequestBuilder

object APIEndpoints {

  val userNameFeeder: BatchableFeederBuilder[String] = csv("usernames.csv").circular

  val price: HttpRequestBuilder = http("price")
    .get("/price")
    .check(status.is(200))
    .check(jmesPath("price").saveAs("price"))

  val priceHistory: HttpRequestBuilder = http("Price History")
    .get("/priceHistory")
    .check(status.is(200))
    .check(jmesPath("priceHistory").saveAs("priceHistory"))

  val sellRequest: HttpRequestBuilder = http("Trade")
    .post("/user/${name}/trade")
    .check(status.is(200))
}
