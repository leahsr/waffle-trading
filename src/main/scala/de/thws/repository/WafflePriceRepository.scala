package de.thws.repository

import de.thws.database.TransactionUtils
import de.thws.domain.{Price, WafflePrice, WafflePriceHistory}
import de.thws.repository.constants.{Properties, Tables}

import java.sql.{Connection, Timestamp}
import scala.collection.mutable.ListBuffer

class WafflePriceRepository {

  def add(wafflePrice: WafflePrice, transaction: Connection): Unit = {

    val add =
      s"""
         |INSERT INTO ${Tables.Price} (
         |  ${Properties.Price.id},
         |  ${Properties.Price.price}
         |)
         |VALUES (?,?)
         |""".stripMargin

    TransactionUtils.execute(add, transaction) { prepare =>
      prepare.setTimestamp(1, Timestamp.from(wafflePrice.timestamp))
      prepare.setDouble(2, wafflePrice.price.value)
    }
  }

  def wafflePriceHistory(transaction: Connection, amount: Int = 250): WafflePriceHistory = {
    val select =
      s"""
         |SELECT
         |  ${Properties.Price.id},
         |  ${Properties.Price.price}
         |FROM ${Tables.Price}
         |ORDER BY ${Properties.Price.id} DESC
         |LIMIT $amount
         |""".stripMargin

    TransactionUtils.executeQuery(select, transaction)(
      {
        prepare => ()
      }, { resultSet =>
        val wafflePrices = ListBuffer[WafflePrice]()
        while (resultSet.next()) {
          wafflePrices.append(
            WafflePrice(
              Price(resultSet.getDouble(Properties.Price.price)),
              resultSet.getTimestamp(Properties.Price.id).toInstant
            )
          )
        }
        WafflePriceHistory(wafflePrices
          .toSeq
        )
      }
    )
  }
}
