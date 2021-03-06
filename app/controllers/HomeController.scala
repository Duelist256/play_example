package controllers

import akka.util.ByteString
import javax.inject._
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def greeting(name: String) = Action { implicit request: Request[AnyContent] =>
    Ok(s"Hello, $name!")
  }

  def echo = Action { implicit request =>
    Ok("Got request [" + request + "]")
  }

  def index2: Action[AnyContent] = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello World! Whacha doin?"), Some("text/plain"))
    )
  }

  def redirect: Action[AnyContent] = Action {
    Redirect("/greetingPetuch")
  }

  def dummyPage(name: String): Action[AnyContent] = TODO
}
