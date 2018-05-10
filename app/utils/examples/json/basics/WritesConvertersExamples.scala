package utils.examples.json.basics

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

object WritesConvertersExamples extends App {

  // basic types
  val jsonString = Json.toJson("Fiver")
  val jsonNumber = Json.toJson(4)
  val jsonBoolean = Json.toJson(false)

  // collections of basic types
  val jsonArrayOfInts = Json.toJson(Seq(1, 2, 3, 4))
  val jsonArrayOfStrings = Json.toJson(List("Fiver", "Bigwig"))

  case class Location(lat: Double, long: Double)
  case class Resident(name: String, age: Int, role: Option[String])
  case class Place(name: String, location: Location, residents: Seq[Resident])

  // converters
  implicit val locationWrites = new Writes[Location] {
    def writes(location: Location): JsObject = Json.obj(
      "lat" -> location.lat,
      "long" -> location.long
    )
  }

  implicit val residentWrites = new Writes[Resident] {
    def writes(resident: Resident): JsObject = Json.obj(
      "name" -> resident.name,
      "age" -> resident.age,
      "role" -> resident.role
    )
  }

  implicit val placeWrites = new Writes[Place] {
    def writes(place: Place): JsObject = Json.obj(
      "name" -> place.name,
      "location" -> place.location,
      "residents" -> place.residents
    )
  }

  val location = Location(1.3, 2.4)
  val resident = Resident("John Doe", 35, Some("Janitor"))
  val place = Place("John Doe", location, Seq(resident))

  val locationJson: JsValue = Json.toJson(location)
  val residentJson: JsValue = Json.toJson(resident)
  val placeJson: JsValue = Json.toJson(place)

  println(locationJson)
  println(residentJson)
  println(placeJson)
}