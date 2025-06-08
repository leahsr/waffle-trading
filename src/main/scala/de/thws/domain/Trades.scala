package de.thws
package domain

case class BuyRequest(userName: UserName, amount: Int)
case class SellRequest(userName: UserName, amount: Int)
