package utils.examples.json.basics

import play.api.libs.functional.syntax._
import play.api.libs.json._

object ConvertingFromJsValue extends App {
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
  val place = Place("Buckingham palace", location, Seq(resident))

  val locationJson: JsValue = Json.toJson(location)
  val residentJson: JsValue = Json.toJson(resident)
  val placeJson: JsValue = Json.toJson(place)

  // minified and readable strings
  println("Minified string:\n" + Json.stringify(placeJson))
  println("Readable string:\n" + Json.prettyPrint(placeJson))

  // Using JsValue.as/asOpt
  println("\n- Using JsValue.as/asOpt:")
  println("Place name: " + (placeJson \ "name").as[String])
  println("Names: " + (placeJson \\ "name").map(_.as[String]))
//  println("Bogus: " + (placeJson \ "bogus").as[String]) // JsResultException
  println("Place name optional: " + (placeJson \ "name").asOpt[String])
  println("Bogus optional: " + (placeJson \ "bogus").asOpt[String])

}
