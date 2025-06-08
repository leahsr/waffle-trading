package de.thws.json

import de.thws.domain.{BuyRequest, UserName}
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.DefaultJsonProtocol.IntJsonFormat
import spray.json.{JsValue, RootJsonReader}


class BuyRequestJsonFormat extends RootJsonReader[BuyRequest] {

  override def read(json: JsValue): BuyRequest = {
    
    val fields = json.asJsObject.fields
    
    val userName = fields(BuyRequestJsonFormat.userName).convertTo[String]
    val quantity = fields(BuyRequestJsonFormat.quantity).convertTo[Int]
    
    BuyRequest(
      UserName(userName),
      quantity
    )
  }
}

object BuyRequestJsonFormat {
  
  val quantity = "quantity"
  val userName = "name"
}
