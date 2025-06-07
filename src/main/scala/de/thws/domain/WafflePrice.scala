package de.thws
package domain

case class WafflePrice(price: Double, lastUpdated: Long = System.currentTimeMillis())
