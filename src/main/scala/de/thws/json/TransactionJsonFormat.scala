package de.thws.json

import de.thws.domain.WaffleTransaction
import spray.json.{JsObject, JsString, JsValue, RootJsonWriter}

class TransactionJsonFormat extends RootJsonWriter[WaffleTransaction]{
  
  override def write(obj: WaffleTransaction): JsValue = {
    
    JsObject(

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
