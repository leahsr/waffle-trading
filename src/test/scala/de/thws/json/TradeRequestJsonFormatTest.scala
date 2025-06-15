package de.thws.json

import de.thws.domain.{Quantity, TradeRequest, WaffleTransactionType}
import de.thws.json.TradeRequestJsonFormat.given
import io.circe.parser
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TradeRequestJsonFormatTest extends AnyFlatSpec with Matchers {


  it should "parse a json to BuyRequest" in {

    val name = "Max Mustermann"
    val quantity = 5
    val transactionType = WaffleTransactionType.Buy

    val json =
      s"""
         |{
         |  "${TradeRequestJsonFormat.quantity}": $quantity,
         |  "${TradeRequestJsonFormat.transactionType}": "${transactionType.value}"
         |}
         |""".stripMargin

    val actualResult = parser.decode[TradeRequest](json).toTry.get
    val expectedResult = TradeRequest(Quantity(quantity), transactionType)

    actualResult shouldEqual expectedResult
  }

}
