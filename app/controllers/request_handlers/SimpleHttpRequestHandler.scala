package controllers.request_handlers

import javax.inject.Inject
import play.api.http.HttpRequestHandler
import play.api.mvc.{Action, Handler, RequestHeader, Results}
import play.api.routing.Router

class SimpleHttpRequestHandler @Inject() (router: Router) extends HttpRequestHandler {
  def handlerForRequest(request: RequestHeader): (RequestHeader, Handler) = {
    router.routes.lift(request) match {
      case Some(handler) => (request, handler)
      case None => (request, Action(Results.NotFound))
    }
  }
}
