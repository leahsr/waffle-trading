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

    val (json,wafflePrice) = PriceJsonFormatTest.defaultFixture()
    
    val priceHistory = WafflePriceHistory(
      Seq(wafflePrice,wafflePrice,wafflePrice)
    )
    
    val expectedJson = JsonParser(
      s"""
         |[$json, $json, $json]
         |""".stripMargin
    )
    
    priceHistoryJsonFormat.write(priceHistory) shouldEqual expectedJson
    
  }

}
