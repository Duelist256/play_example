package utils.examples.json.basics

import play.api.libs.functional.syntax._
import play.api.libs.json._

object TraversingExample extends App {
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

  // simple path '\'
  println("- Using simple path \\")
  println("Latitude: " + (placeJson \ "location" \ "lat").get)
  println("0th resident: " + (placeJson \ "residents" \ 0).get)

  // recursive path '\\'
  println("\n- Using recursive path \\\\")
  println("Names: " + (placeJson \\ "name"))

  // direct lookup, apply is the same as '\' but returns value directly
  // or throws an exception if not found
  println("\n- Using direct lookup (apply)")
  println("Place name: " + placeJson("name"))
  println("0th resident: " + placeJson("residents")(0))
//  println("3th resident: " + placeJson("residents")(3)) // IndexOfBoundException
//  println("bogus: " + placeJson("bogus")) // NoSuchElementException
}
