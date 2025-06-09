package de.thws
package domain

case class TradeRequest(userName: UserName, quantity: Quantity, transactionType: WaffleTransactionType)
