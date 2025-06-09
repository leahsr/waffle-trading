package de.thws.repository

import de.thws.database.TransactionUtils
import de.thws.domain.WafflePrice
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
      prepare.setDouble(2, wafflePrice.price)
    }
  }

  def wafflePriceHistory(transaction: Connection): Seq[WafflePrice] = {
    val select =
      s"""
         |SELECT
         |  ${Properties.Price.id},
         |  ${Properties.Price.price}
         |FROM ${Tables.Price}
         |""".stripMargin

    TransactionUtils.executeQuery(select, transaction)(
      {
        prepare => ()
      }, { resultSet =>
        val wafflePrices = ListBuffer[WafflePrice]()
        while (resultSet.next()) {
          wafflePrices.append(
            WafflePrice(
              resultSet.getDouble(Properties.Price.price),
              resultSet.getTimestamp(Properties.Price.id).toInstant
            )
          )
        }
        wafflePrices
          .sortBy(_.timestamp)
          .toSeq
      }
    )
  }
}
