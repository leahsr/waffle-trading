package de.thws.json

import de.thws.domain.{TradeRequest, UserName}
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.DefaultJsonProtocol.IntJsonFormat
import spray.json.{JsValue, RootJsonReader}


class TradeRequestJsonFormat extends RootJsonReader[TradeRequest] {

  override def read(json: JsValue): TradeRequest = {
    
    val fields = json.asJsObject.fields
    
    val userName = fields(TradeRequestJsonFormat.userName).convertTo[String]
    val quantity = fields(TradeRequestJsonFormat.quantity).convertTo[Int]
    
    TradeRequest(
      UserName(userName),
      quantity
    )
  }
}

object TradeRequestJsonFormat {
  
  val quantity = "quantity"
  val userName = "name"
}
