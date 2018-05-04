package controllers.action_composition

import play.api.Logger
import play.api.mvc.{Action, BodyParser, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

case class Logging[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] = {
    Logger.info("Calling action")
    action(request)
  }

  override def parser: BodyParser[A] = action.parser
  override def executionContext: ExecutionContext = action.executionContext
}
