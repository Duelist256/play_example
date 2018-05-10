package utils.examples.json.basics

import play.api.libs.json._
import play.api.libs.functional.syntax._

object WritesConvertersWithCombinatorPattern extends App {

  case class Location(lat: Double, long: Double)
  case class Resident(name: String, age: Int, role: Option[String])
  case class Place(name: String, location: Location, residents: Seq[Resident])

  // converters
  implicit val locationWrites: Writes[Location] = (
    (JsPath \ "lat").write[Double] and
    (JsPath \ "long").write[Double]
  )(unlift(Location.unapply))

  implicit val residentWrites: Writes[Resident] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "age").write[Int] and
      (JsPath \ "role").writeNullable[String]
    )(unlift(Resident.unapply))

  implicit val placeWrites: Writes[Place] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "location").write[Location] and
      (JsPath \ "residents").write[Seq[Resident]]
    )(unlift(Place.unapply))

  val location = Location(1.3, 2.4)
  val resident = Resident("John Doe", 35, Some("Janitor"))
  val place = Place("John Doe", location, Seq(resident))

  println(Json.toJson(location))
  println(Json.toJson(resident))
  println(Json.toJson(place))
}