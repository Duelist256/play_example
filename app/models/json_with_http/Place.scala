package models.json_with_http

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Place(name: String, location: Location)

object Place {

  var list: List[Place] =
    List(
      Place(
        "Sandleford",
        Location(51.377797, -1.318965)
      ),
      Place(
        "Watership Down",
        Location(51.235685, -1.309197)
      )
    )

  def save(place: Place): Unit = {
    list = list ::: List(place)
  }
}