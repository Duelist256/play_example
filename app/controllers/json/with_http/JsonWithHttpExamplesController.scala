package controllers.json.with_http

import javax.inject.Inject
import play.api.libs.json.{JsError, JsValue, Json, Reads}
import play.api.mvc.{AbstractController, Action, BodyParser, ControllerComponents}
import models.json_with_http.Place

import scala.concurrent.ExecutionContext

class JsonWithHttpExamplesController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def listPlaces = Action {
    import models.json_with_http.Converters._
    val json = Json.toJson(Place.list)
    Ok(json)
  }

  def savePlace: Action[JsValue] = Action(parse.json) { request =>
    import models.json_with_http.Converters._
    val placeResult = request.body.validate[Place]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
      },
      place => {
        Place.save(place)
        Ok(Json.obj("status" -> "OK", "message" -> s"Place '${place.name}' saved."))
      }
    )
  }

  def validateJson[A : Reads]: BodyParser[A] = {
    parse.json.validate(
      _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
    )
  }


  def savePlaceConcise: Action[Place] = {
    import models.json_with_http.ConvertersWithConciseReads._
    Action(validateJson[Place]) { request =>
      // `request.body` contains a fully validated `Place` instance.
      val place = request.body
      Place.save(place)
      Ok(Json.obj("status" ->"OK", "message" -> ("Place '"+place.name+"' saved.") ))
    }
  }
}

