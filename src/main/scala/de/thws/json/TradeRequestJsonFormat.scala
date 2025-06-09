package de.thws.json

import de.thws.domain.{TradeRequest, UserName, WaffleTransactionType}
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.DefaultJsonProtocol.IntJsonFormat
import spray.json.{JsValue, RootJsonReader}

trait TradeRequestJsonFormatComponent {

  implicit val tradeRequestJsonFormat: TradeRequestJsonFormat = new TradeRequestJsonFormat

  class TradeRequestJsonFormat extends RootJsonReader[TradeRequest] {

    override def read(json: JsValue): TradeRequest = {

      val fields = json.asJsObject.fields

      val userName = fields(TradeRequestJsonFormat.userName).convertTo[String]
      val quantity = fields(TradeRequestJsonFormat.quantity).convertTo[Int]
      val transactionType = fields(TradeRequestJsonFormat.transactionType).convertTo[String]

      TradeRequest(
        UserName(userName),
        quantity,
        WaffleTransactionType(transactionType)
      )
    }
  }
}

object TradeRequestJsonFormat {
  
  val quantity = "quantity"
  val userName = "name"
  val transactionType = "transactionType"
}
