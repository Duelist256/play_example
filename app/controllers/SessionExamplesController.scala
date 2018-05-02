package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class SessionExamplesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def replaceSession() = Action { implicit request =>
    Ok("Welcome").withSession("connected" -> "user@gmail.com")
  }

  def addSession() = Action { implicit request =>
    Ok("Welcome").withSession(request.session + ("newSession" -> "user2@gmail.com"))
  }

  def removeSession() = Action { implicit request =>
    Ok("Welcome").withSession(request.session - "newSession")
  }

  def showSession() = Action { implicit request =>
    request.session.get("connected").map { user =>
      Ok("Hello " + user)
    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }
  }

  def showSession2() = Action { implicit request =>
    request.session.get("newSession").map { user =>
      Ok("Hello " + user)
    }.getOrElse {
      val addSessionRoute = routes.SessionExamplesController.addSession().relative
      Unauthorized(
        s"Oops, there is no such session value! <a href=$addSessionRoute>Add session</a>").as(HTML)
    }
  }

  def discardSession() = Action { implicit request =>
    Ok("Session was discarded").withNewSession
  }

  def showFlash = Action { implicit request =>
    Ok {
      request.flash.get("success").getOrElse("Welcome!")
    }
  }

  def saveFlash = Action { implicit request =>
    val showFlashRoute = routes.SessionExamplesController.showFlash().relative
    Redirect(showFlashRoute).flashing(
      "success" -> "The item has been created")
  }
}