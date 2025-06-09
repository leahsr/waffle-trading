package de.thws.json

import de.thws.domain.WaffleTransaction
import spray.json.{JsNumber, JsObject, JsString, JsValue, RootJsonWriter}

class TransactionJsonFormat extends RootJsonWriter[WaffleTransaction]{
  
  override def write(obj: WaffleTransaction): JsValue = {
    
    JsObject(
      TransactionJsonFormat.id -> JsNumber(obj.id.value),
      TransactionJsonFormat.timestamp -> JsString(obj.timestamp.toString),
      TransactionJsonFormat.transactionType -> JsString(obj.transactionType.value),
      TransactionJsonFormat.quantity -> JsNumber(obj.quantity.value),
      TransactionJsonFormat.userName -> JsString(obj.userName.value)
    )
  }
}

object TransactionJsonFormat {
  
   val id = "id"
   val timestamp = "timestamp"
   val transactionType = "transactionType"
   val price = "price"
   val quantity = "quantity"
   val userName = "userName"
}
