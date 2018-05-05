package controllers.content_negotiations

import javax.inject._
import play.api.mvc._

@Singleton
class ContentNegotiationsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def showAcceptedLanguages() = Action { implicit request =>
    Ok(request.acceptLanguages.toString())
  }

  def showAcceptedTypes() = Action { request =>
    Ok(request.acceptedTypes.toString())
  }

  def testRender() = Action { implicit request =>
    render {
      case Accepts.Json() => Ok("It is a JSON!")
      case Accepts.Html() => Ok("It is a HTML!")
    }
  }
}