package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ResultExamplesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def returnHtml() = Action {
    Ok("<h1>KPACUBO</h1>").as(HTML)
  }

  def returnHtmlWithHeaders() = Action {
    Ok("<h1>HEADERS!</h1>").as(HTML).withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xx")
  }

  def returnHtmlWithCookies = Action {
    Ok("<h1>COOKIES!</h1>").as(HTML).withCookies(Cookie("theme", "blue")).bakeCookies()
  }

  def discardCookies = Action {
    Ok("<h1>DISCARD COOKIES!</h1>").as(HTML).discardingCookies(DiscardingCookie("theme"))
  }
}