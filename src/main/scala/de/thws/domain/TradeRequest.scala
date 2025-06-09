package de.thws
package domain

case class TradeRequest(quantity: Quantity, transactionType: WaffleTransactionType) {
  def toCommand(userName: UserName): WaffleTransactionCommand =
    WaffleTransactionCommand(
      userName = userName,
      transactionType = transactionType,
      quantity = quantity
    )
}

