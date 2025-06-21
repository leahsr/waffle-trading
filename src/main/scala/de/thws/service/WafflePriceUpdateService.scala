package de.thws
package service

import de.thws.domain.{Price, WafflePrice}

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.duration.DurationInt
import scala.util.Random

class WafflePriceUpdateService(
                                wafflePriceService: WafflePriceService,
                                val initial: Double = 2.5
                              ) {

  private val currentPriceReference = new AtomicReference(WafflePrice(Price(this.initial)))
  private val ticker = new java.util.Timer()

  def currentPrice: WafflePrice = currentPriceReference.get()

  private def init(): Unit = {

    val scheduler = new Runnable {
      def run(): Unit = updatePrice()
    }

    ticker.scheduleAtFixedRate(new java.util.TimerTask {
      def run(): Unit = scheduler.run()
    }, 0, 30.seconds.toMillis)
  }

  private def updatePrice(): Unit = {
    val old = currentPriceReference.get()
    val change = Random.between(-0.2, 0.2)
    val newPrice = ((old.price.value + change).max(0.5) * 100).round / 100
    val newWafflePrice = WafflePrice(Price(newPrice))
    
    println(s"New Price: $newWafflePrice")
    wafflePriceService.add(newWafflePrice)
    currentPriceReference.set(newWafflePrice)
  }

  init()
}
