package de.thws.domain

import org.postgresql.util.PGobject

import java.time.Instant

case class TransactionId(value: Int)

case class UserName(value: String)

case class TransactionQuantity(value: Int)

case class TransactionPrice(value: Double)

sealed abstract class WaffleTransactionType(val value: String) {
  def pgEnum(): PGobject = {
    val pgEnum = new PGobject()
    pgEnum.setType("transaction_type")
    pgEnum.setValue(this.value)
    pgEnum
  }
}

object WaffleTransactionType {
  def apply(value: String): WaffleTransactionType = {
    value match {
      case "sell" => Sell
      case "buy" => Buy
    }
  }

  case object Sell extends WaffleTransactionType("sell")

  case object Buy extends WaffleTransactionType("buy")
}


case class WaffleTransaction(
                              id: Option[TransactionId] = None,
                              timestamp: Instant = Instant.now(),
                              transactionType: WaffleTransactionType,
                              price: TransactionPrice,
                              quantity: TransactionQuantity,
                              userName: UserName,
                            )

case class WaffleTransactions(
                               values: Seq[WaffleTransaction]
                             )

object WaffleTransactions {
  def from(values: Iterable[WaffleTransaction]): WaffleTransactions = WaffleTransactions(values.toSeq)
}