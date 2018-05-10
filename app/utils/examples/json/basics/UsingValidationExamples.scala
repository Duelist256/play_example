package utils.examples.json.basics

import play.api.libs.functional.syntax._
import play.api.libs.json._

object UsingValidationExamples extends App {
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


  val nameResult: JsResult[String] = (placeJson \ "name").validate[String]

  // Pattern matching
  println("- Pattern matching of JsResult:")
  nameResult match {
    case s: JsSuccess[String] => println("Name: " + s.get)
    case e: JsError => println("Errors: " + JsError.toJson(e).toString())
  }

  // Fallback value
  val nameOrFallback = nameResult.getOrElse("Undefined")
  println("\nFallback value: " + nameOrFallback)

  // map
  val nameUpperResult: JsResult[String] = nameResult.map(_.toUpperCase())
  println("\nName upper result: " + nameUpperResult)

  // fold
  println("\n- Fold:")
  val nameOption: Option[String] = nameResult.fold(
    invalid = {
      fieldErrors =>
        fieldErrors.foreach(x => {
          println("field: " + x._1 + ", errors: " + x._2)
        })
        None
    },
    valid = {
      name => Some(name)
    }
  )
  println("Name option: " + nameOption)
}
