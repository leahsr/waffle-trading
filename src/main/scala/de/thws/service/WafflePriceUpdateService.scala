package de.thws
package service

import com.typesafe.scalalogging.LazyLogging
import de.thws.domain.{Price, WafflePrice}

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.duration.DurationInt
import scala.util.Random

class WafflePriceUpdateService(
                                wafflePriceService: WafflePriceService,
                                val initial: Double = 2.5
                              ) extends LazyLogging {

  private val currentPriceReference = new AtomicReference(WafflePrice(Price(this.initial)))
  private val ticker = new java.util.Timer()

  def currentPrice: WafflePrice = currentPriceReference.get()

  private def init(): Unit = {

    val scheduler = new Runnable {
      def run(): Unit = updatePrice()
    }

    ticker.scheduleAtFixedRate(new java.util.TimerTask {
      def run(): Unit = scheduler.run()
    }, 0, 1.seconds.toMillis)
  }

  private def updatePrice(): Unit = {
    val old = currentPriceReference.get()
    val change = Random.between(-0.2, 0.2)
    val newPrice = ((old.price.value + change).max(0.5) * 100.0).round / 100.0
    val newWafflePrice = WafflePrice(Price(newPrice))
    
    logger.info(s"New Price: $newWafflePrice")
    wafflePriceService.add(newWafflePrice)
    currentPriceReference.set(newWafflePrice)
  }

  init()
}
