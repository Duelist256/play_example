package controllers

import java.io.File

import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

@Singleton
class BodyParserExamples @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def saveJson() = Action { request: Request[AnyContent] =>
    val body: AnyContent = request.body
    val jsonBody: Option[JsValue] = body.asJson

    jsonBody.map { json =>
      Ok("Got: " + (json \ "name").as[String])
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }
  }

  def saveJson2(): Action[JsValue] = Action(parse.json) { request: Request[JsValue] =>
    Ok("Hello, " + (request.body \ "name").as[String])
  }

  def tolerantJson(): Action[JsValue] = Action(parse.tolerantJson) { request: Request[JsValue] =>
    Ok("Hi, " + (request.body \ "name").as[String])
  }

  def saveToFile(): Action[File] = Action(parse.file(to = new File("body.txt"))) { request: Request[File] =>
    Ok("Saved the request content to " + request.body)
  }

  // Accept only 10 bytes of data otherwise 413 error
  def restrictedLength(): Action[String] = Action(parse.text(maxLength = 10)) { request: Request[String] =>
    Ok("Got: " + request.body)
  }
}