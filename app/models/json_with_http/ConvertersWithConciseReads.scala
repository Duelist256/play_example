package models.json_with_http

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object ConvertersWithConciseReads {

  // Writes
  implicit val locationWrites: Writes[Location] = (
    (JsPath \ "lat").write[Double] and
    (JsPath \ "long").write[Double]
   )(unlift(Location.unapply))

  implicit val placeWrites: Writes[Place] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "location").write[Location]
  )(unlift(Place.unapply))

  // Reads
  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double](min(-90.0) keepAnd max(90.0)) and
    (JsPath \ "long").read[Double](min(-180.0) keepAnd max(180.0))
  )(Location.apply _)

  implicit val placeReads: Reads[Place] = (
    (JsPath \ "name").read[String](minLength[String](2)) and
    (JsPath \ "location").read[Location]
  )(Place.apply _)
}
