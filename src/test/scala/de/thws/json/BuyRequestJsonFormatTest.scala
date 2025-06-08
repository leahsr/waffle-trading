package de.thws.json

import de.thws.domain.{BuyRequest, UserName}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json.JsonParser

class BuyRequestJsonFormatTest extends AnyFlatSpec with Matchers {
  
  val buyRequestJsonFormat: BuyRequestJsonFormat = new BuyRequestJsonFormat()
  
  it should "parse a json to BuyRequest" in {
    
    val name = "Max Mustermann"
    val quantity = 5
    
    val json = JsonParser(
      s"""
         |{
         |  "name": "$name",
         |  "quantity": $quantity
         |}
         |""".stripMargin
    )
    
    val expectedResult = BuyRequest(UserName(name), quantity)
    
    expectedResult shouldEqual buyRequestJsonFormat.read(json)
  }

}
