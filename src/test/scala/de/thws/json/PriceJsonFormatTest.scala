package de.thws.json

import de.thws.domain.{Price, WafflePrice}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import spray.json.{JsValue, JsonParser}

import java.time.Instant
import java.time.temporal.ChronoUnit

class PriceJsonFormatTest extends AnyFlatSpec with Matchers {
  
  val priceJsonFormat = PriceJsonFormat()
  
  it should "parse waffle price to json" in {
    
    val (json, wafflePrice) = PriceJsonFormatTest.defaultFixture()
    
    priceJsonFormat.write(wafflePrice) shouldEqual json 
  }
}

object PriceJsonFormatTest {
  
  def defaultFixture(): (JsValue, WafflePrice) = {

    val time = Instant.now().plus(10, ChronoUnit.SECONDS)
    val price = Price(2.39)
    
    val wafflePrice = WafflePrice(price, time)
    
    val json = JsonParser(
      s"""
         |{
         |  "${PriceJsonFormat.price}": ${price.value},
         |  "${PriceJsonFormat.timestamp}": "$time"
         | }
         |""".stripMargin
    )

    (json, wafflePrice)
  }
}
