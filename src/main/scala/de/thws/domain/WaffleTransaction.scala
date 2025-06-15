package de.thws.domain

import de.thws.domain.Price
import org.postgresql.util.PGobject

import java.time.Instant

case class TransactionId(value: Int)

case class UserName(value: String)

case class Quantity(value: Int)

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

case class WaffleTransactionCommand(
                                     transactionType: WaffleTransactionType,
                                     quantity: Quantity,
                                     userName: UserName,
                                     timestamp: Instant = Instant.now(),
                                   )

case class WaffleTransaction(
                              id: TransactionId,
                              timestamp: Instant = Instant.now(),
                              transactionType: WaffleTransactionType,
                              price: Price,
                              quantity: Quantity,
                              userName: UserName,
                            )

case class WaffleTransactions(
                               values: Seq[WaffleTransaction]
                             )

object WaffleTransactions {
  def from(values: Iterable[WaffleTransaction]): WaffleTransactions = WaffleTransactions(values.toSeq)
}