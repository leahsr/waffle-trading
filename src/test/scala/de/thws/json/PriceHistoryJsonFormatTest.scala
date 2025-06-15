package de.thws.json

import de.thws.domain.WafflePriceHistory
import de.thws.json.PriceHistoryJsonFormat.given
import io.circe.*
import io.circe.syntax.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PriceHistoryJsonFormatTest extends AnyFlatSpec with Matchers {

  it should "parse history to json" in {

    val (json, wafflePrice) = PriceJsonFormatTest.defaultFixture()

    val priceHistory = WafflePriceHistory(
      Seq(wafflePrice, wafflePrice, wafflePrice)
    )

    val expectedJson = parser.parse(
      s"""
         |[$json, $json, $json]
         |""".stripMargin
    ).toTry.get

    priceHistory.asJson shouldEqual expectedJson

  }

}
