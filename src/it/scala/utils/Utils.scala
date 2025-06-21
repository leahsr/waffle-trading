package utils

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.util.Properties

object Utils {
  val baseUrl: String = Properties.envOrElse("WAFFLE_API_URL", "http://localhost:8080")
  val baseHttpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
}
