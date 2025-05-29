package de.thws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import de.thws.route.WaffleTradingRoute

object Boot extends App {
  
  implicit val system: ActorSystem = ActorSystem("waffle-trading")
  
  try {
    
    val server = Http().newServerAt("localhost", 8080).bind(new WaffleTradingRoute().route)
    println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  } catch {
    case e: Throwable => {
      println(s"Could not start the application due to error $e, forcing shutdown")
      System.exit(1)
    }
  }

}
