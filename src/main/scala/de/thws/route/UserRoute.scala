package de.thws.route

class UserRoute {
  val route =
    path("users" / Segment) { userName =>
      // URL-encode den Benutzernamen
      val encodedUserName = URLEncoder.encode(userName, StandardCharsets.UTF_8.toString)

      // Hier kannst du den Benutzernamen weiterverarbeiten
      get {
        complete(s"Benutzername: $encodedUserName")
      }
    }
}
