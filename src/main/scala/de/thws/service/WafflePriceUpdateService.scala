package de.thws
package service

import de.thws.domain.{Price, WafflePrice}

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.Future
import scala.util.Random

class WafflePriceUpdateService(
                                wafflePriceService: WafflePriceService,
                                val initial: Double = 2.5
                              ) {

  private val priceRef = new AtomicReference(WafflePrice(Price(this.initial)))

  private val scheduler = new Runnable {
    def run(): Unit = {
      val old = priceRef.get()
      val change = Random.between(-0.2, 0.2)
      val newPrice = (old.price.value + change).max(0.5)
      println(s"New price: $newPrice")
      val newWafflePrice = WafflePrice(Price(BigDecimal(newPrice).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble))
      wafflePriceService.add(newWafflePrice)
      priceRef.set(newWafflePrice)
    }
  }

  private val ticker = new java.util.Timer()
  ticker.scheduleAtFixedRate(new java.util.TimerTask {
    def run(): Unit = scheduler.run()
  }, 0, 30000) // 30 Sekunden

  def currentPrice: WafflePrice = priceRef.get()
}
