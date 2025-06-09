package de.thws
package domain

case class TradeRequest(userName: UserName, quantity: Quantity, transactionType: WaffleTransactionType) {
  def toCommand(username: UserName): WaffleTransactionCommand =
    WaffleTransactionCommand(
      userName = userName,
      transactionType = transactionType,
      quantity = quantity
    )
}

