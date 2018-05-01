package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class RoutingExamplesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def fixedValue(name: String) = Action { implicit request: Request[AnyContent] =>
    Ok(s"Hello, $name! This is test of fixed value.")
  }

  def defaultValue(name: String) = Action { implicit request: Request[AnyContent] =>
    Ok(s"Hello, $name! This is test of default value.")
  }

  def optionalValue(name: Option[String]) = Action { implicit request: Request[AnyContent] =>
    Ok(s"Hello, ${
      name match {
        case Some(v) => v
        case None => "LoL"
      }}! This is test of default value.")
  }

  def hello(name: String) = Action {
    Ok("Hello " + name + "!")
  }

   def helloBob = Action {
     Redirect(routes.RoutingExamplesController.hello("Rob!!!!"))
   }

}
