package de.thws.repository

import de.thws.database.TransactionUtils
import de.thws.domain.*
import de.thws.repository.constants.{Properties, Tables}

import java.sql.{Connection, Timestamp}
import scala.collection.mutable.ListBuffer

class WaffleTransactionsRepository {
  
  def add(waffleTransactionCommand: WaffleTransactionCommand, wafflePrice: WafflePrice, transaction: Connection): Option[WaffleTransaction] = {
    
    val add =
      s"""
         |INSERT INTO ${Tables.Transaction} (
         |  ${Properties.WaffleTransaction.timestamp},
         |  ${Properties.WaffleTransaction.transactionType},
         |  ${Properties.Price.id},
         |  ${Properties.WaffleTransaction.quantity},
         |  ${Properties.WaffleTransaction.userName}
         |)
         |VALUES (?,?,?,?,?) 
         |RETURNING ${Properties.WaffleTransaction.id}
         |""".stripMargin

    val id = TransactionUtils.executeQuery(add, transaction) ({ prepare =>
      prepare.setTimestamp(1, Timestamp.from(waffleTransactionCommand.timestamp))
      prepare.setObject(2, waffleTransactionCommand.transactionType.pgEnum())
      prepare.setTimestamp(3, Timestamp.from(wafflePrice.timestamp))
      prepare.setInt(4, waffleTransactionCommand.quantity.value)
      prepare.setString(5, waffleTransactionCommand.userName.value)
    },
    { resultSet =>
      assert(resultSet.next())
      resultSet.getInt(Properties.WaffleTransaction.id)
    })
    
    this.waffleTransaction(id, transaction)
  }
  
  def waffleTransaction(id: Int, transaction: Connection): Option[WaffleTransaction] = {
    
    val select =
        s"""
           |SELECT 
           |  ${Properties.WaffleTransaction.id}, 
           |  ${Properties.WaffleTransaction.timestamp}, 
           |  ${Properties.WaffleTransaction.transactionType}, 
           |  (
           |    SELECT ${Properties.Price.price}
           |    FROM ${Tables.Price} as ${Properties.Price.price}
           |    WHERE ${Tables.Transaction}.${Properties.Price.id} = ${Tables.Price}.${Properties.Price.id}
           |  ) as ${Properties.Price.price},    
           |  ${Properties.WaffleTransaction.quantity}, 
           |  ${Properties.WaffleTransaction.userName}
           |FROM ${Tables.Transaction}
           |WHERE ${Properties.WaffleTransaction.id} = ?
           |""".stripMargin

      TransactionUtils.executeQuery(select, transaction)(
        { prepare =>
          prepare.setInt(1, id)
        },
        { resultSet =>
          if (resultSet.next()) {
              Some(WaffleTransaction(
                TransactionId(resultSet.getInt(Properties.WaffleTransaction.id)),
                resultSet.getTimestamp(Properties.WaffleTransaction.timestamp).toInstant,
                WaffleTransactionType(resultSet.getString(Properties.WaffleTransaction.transactionType)),
                Price(resultSet.getDouble(Properties.Price.price)),
                Quantity(resultSet.getInt(Properties.WaffleTransaction.quantity)),
                UserName(resultSet.getString(Properties.WaffleTransaction.userName)),
              ))
          } else None
        })
  }

  def waffleTransactions(userName: UserName, transaction: Connection): WaffleTransactions = {
    val select =
      s"""
         |SELECT 
         |  ${Properties.WaffleTransaction.id}, 
         |  ${Properties.WaffleTransaction.timestamp}, 
         |  ${Properties.WaffleTransaction.transactionType}, 
         |  (
         |    SELECT ${Properties.Price.price}
         |    FROM ${Tables.Price} as ${Properties.Price.price}
         |    WHERE ${Tables.Transaction}.${Properties.Price.id} = ${Tables.Price}.${Properties.Price.id}
         |  ) as ${Properties.Price.price},    
         |  ${Properties.WaffleTransaction.quantity}, 
         |  ${Properties.WaffleTransaction.userName}
         |FROM ${Tables.Transaction}
         |WHERE ${Properties.WaffleTransaction.userName} ILIKE ?
         |""".stripMargin

    TransactionUtils.executeQuery(select, transaction)(
      { prepare =>
        prepare.setString(1, userName.value)
      },
      { resultSet =>
        val waffleTransactions = ListBuffer[WaffleTransaction]()
        while (resultSet.next()) {
          waffleTransactions.append(
            WaffleTransaction(
              TransactionId(resultSet.getInt(Properties.WaffleTransaction.id)),
              resultSet.getTimestamp(Properties.WaffleTransaction.timestamp).toInstant,
              WaffleTransactionType(resultSet.getString(Properties.WaffleTransaction.transactionType)),
              Price(resultSet.getDouble(Properties.Price.price)),
              Quantity(resultSet.getInt(Properties.WaffleTransaction.quantity)),
              UserName(resultSet.getString(Properties.WaffleTransaction.userName)),
            )
          )
        }
        WaffleTransactions.from(waffleTransactions)
      }
    )
  }
}
