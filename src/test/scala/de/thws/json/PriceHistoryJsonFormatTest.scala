package de.thws.json

import de.thws.domain.{Price, WafflePrice, WafflePriceHistory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import spray.json.JsonParser

import java.time.Instant
import java.time.temporal.ChronoUnit

class PriceHistoryJsonFormatTest extends AnyFlatSpec with Matchers {
  
  val priceJsonFormat = new PriceJsonFormat
  val priceHistoryJsonFormat = new PriceHistoryJsonFormat(priceJsonFormat)

  it should "parse history to json" in {

    val time1 = Instant.now().plus(10, ChronoUnit.SECONDS)
    val time2 = Instant.now().plus(20, ChronoUnit.SECONDS)
    val time3 = Instant.now().plus(30, ChronoUnit.SECONDS)

    val priceHistory = WafflePriceHistory(
      Seq(
        WafflePrice(Price(2.39), time1),
        WafflePrice(Price(2.83), time2),
        WafflePrice(Price(4.39), time3),
      )
    )
    
    val expectedJson = JsonParser(
      s"""
         |[
         | {
         |  "${PriceJsonFormat.price}": 2.39,
         |  "${PriceJsonFormat.timestamp}": "$time1"
         | },
         | {
         |  "${PriceJsonFormat.price}": 2.83,
         |  "${PriceJsonFormat.timestamp}": "$time2"
         | },
         | {
         | "${PriceJsonFormat.price}": 4.39,
         |  "${PriceJsonFormat.timestamp}": "$time3"
         | }
         |]
         |""".stripMargin
    )
    
    priceHistoryJsonFormat.write(priceHistory) shouldEqual expectedJson
    
  }

}
