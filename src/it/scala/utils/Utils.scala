package utils

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder

object Utils {
  val baseHttpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
}
