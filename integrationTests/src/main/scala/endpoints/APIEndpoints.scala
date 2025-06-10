package endpoints

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.request.builder.HttpRequestBuilder

object APIEndpoints {

  val price: HttpRequestBuilder = http("price")
    .get("/price")
    .check(status.is(200))
    .check(jmesPath("price").saveAs("price"))
}
