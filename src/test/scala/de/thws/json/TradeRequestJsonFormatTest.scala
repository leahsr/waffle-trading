package de.thws.json

import de.thws.domain.{TradeRequest, UserName, WaffleTransactionType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json.JsonParser

class TradeRequestJsonFormatTest extends AnyFlatSpec with Matchers {

  val buyRequestJsonFormat: TradeRequestJsonFormat = new TradeRequestJsonFormat()

  it should "parse a json to BuyRequest" in {

    val name = "Max Mustermann"
    val quantity = 5

    val json = JsonParser(
      s"""
         |{
         |  "${TradeRequestJsonFormat.userName}": "$name",
         |  "${TradeRequestJsonFormat.quantity}": $quantity,
         |  "${TradeRequestJsonFormat.transactionType}": "${WaffleTransactionType.Buy.value}"
         |}
         |""".stripMargin
    )

    val expectedResult = TradeRequest(UserName(name), quantity, WaffleTransactionType.Buy)

    expectedResult shouldEqual buyRequestJsonFormat.read(json)
  }

}
