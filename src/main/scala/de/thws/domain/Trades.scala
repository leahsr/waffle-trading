package de.thws
package domain

case class BuyRequest(user: User, amount: Int)
case class SellRequest(user: User, amount: Int)
