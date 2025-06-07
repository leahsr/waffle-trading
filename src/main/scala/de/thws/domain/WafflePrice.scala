package de.thws
package domain

import java.time.Instant

case class WafflePrice(price: Double, timestamp: Instant = Instant.now())
