package de.thws.json

import de.thws.domain.{Quantity, TradeRequest, UserName, WaffleTransactionType}
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat}
import spray.json.{JsValue, RootJsonReader}

class TradeRequestJsonFormat extends RootJsonReader[TradeRequest] {

  override def read(json: JsValue): TradeRequest = {

    val fields = json.asJsObject.fields
    
    val quantity = fields(TradeRequestJsonFormat.quantity).convertTo[Int]
    val transactionType = fields(TradeRequestJsonFormat.transactionType).convertTo[String]

    TradeRequest(
      Quantity(quantity),
      WaffleTransactionType(transactionType)
    )
  }
}

object TradeRequestJsonFormat {

  val quantity = "quantity"
  val transactionType = "transactionType"
}
