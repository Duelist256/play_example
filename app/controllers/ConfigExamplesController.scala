package controllers

import javax.inject._
import play.api.Configuration
import play.api.ConfigLoader.stringLoader
import play.api.mvc._
import utils.AppConfig

@Singleton
class ConfigExamplesController @Inject()(config: Configuration, cc: ControllerComponents) extends AbstractController(cc) {

  def showConfig() = Action { implicit request =>
    val str = config.get("str")
    val double = config.get("double")
    val int = config.get("int")
    Ok(s"$str\n$int\n$double")
  }

  def showCustomConfig() = Action { implicit request =>
    val appConfig = config.get[AppConfig]("app.config")
    Ok(s"$appConfig")
  }
}