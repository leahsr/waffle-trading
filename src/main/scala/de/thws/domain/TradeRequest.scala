package de.thws
package domain

case class TradeRequest(userName: UserName, amount: Int, transactionType: WaffleTransactionType)
