package de.thws.domain

import java.time.Instant

case class WafflePrice(price: Price, timestamp: Instant = Instant.now())

case class WafflePriceHistory(values: Seq[WafflePrice])

case class Price(value: Double)