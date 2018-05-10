package utils.examples.json.basics

import play.api.libs.functional.syntax._
import play.api.libs.json._

object ConvertJsValueToModel extends App {
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


  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double] and
    (JsPath \ "long").read[Double]
  )(Location.apply _)

  implicit val residentReads: Reads[Resident] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "age").read[Int] and
    (JsPath \ "role").readNullable[String]
  )(Resident.apply _)

  implicit val placeReads: Reads[Place] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "location").read[Location] and
    (JsPath \ "residents").read[Seq[Resident]]
  )(Place.apply _)

  val placeResult = placeJson.validate[Place]
  println("Place result: " + placeResult)

  val zerothResidentResult = (placeJson \ "residents")(0).validate[Resident]
  println("0th resident result: " + zerothResidentResult)
}
