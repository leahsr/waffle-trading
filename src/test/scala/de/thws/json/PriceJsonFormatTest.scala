package de.thws.json

import de.thws.domain.{Price, WafflePrice}
import de.thws.json.PriceJsonFormat.given
import io.circe.*
import io.circe.syntax.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.Instant
import java.time.temporal.ChronoUnit

class PriceJsonFormatTest extends AnyFlatSpec with Matchers {


  it should "parse waffle price to json" in {

    val (json, wafflePrice) = PriceJsonFormatTest.defaultFixture()

    wafflePrice.asJson shouldEqual json
  }
}

object PriceJsonFormatTest {

  def defaultFixture(): (Json, WafflePrice) = {

    val time = Instant.now().plus(10, ChronoUnit.SECONDS)
    val price = Price(2.39)

    val wafflePrice = WafflePrice(price, time)

    val json = parser.parse(
      s"""
         |{
         |  "${PriceJsonFormat.price}": ${price.value},
         |  "${PriceJsonFormat.timestamp}": "$time"
         | }
         |""".stripMargin
    ).toTry.get

    (json, wafflePrice)
  }
}
