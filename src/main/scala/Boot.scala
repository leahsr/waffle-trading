package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import de.thws.database.TransactionServiceJdbc
import de.thws.route.WaffleTradingRoute

import scala.util.Properties

object Boot extends App {
  
  implicit val system: ActorSystem = ActorSystem("waffle-trading")
  
  try {

    val user = Properties.envOrElse("WAFFLE_USER", "waffle")
    val password = Properties.envOrElse("WAFFLE_PASSWORD", "password")
    val url = Properties.envOrElse("WAFFLE_DB_URL", "jdbc:postgresql://localhost:5432/waffle")

    val transactionService = TransactionServiceJdbc(url, user, password)


    val server = Http().newServerAt("localhost", 8080).bind(new WaffleTradingRoute().route)
    println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  } catch {
    case e: Throwable => {
      println(s"Could not start the application due to error $e, forcing shutdown")
      System.exit(1)
    }
  }

}
