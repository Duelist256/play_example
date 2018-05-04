package controllers.action_composition

import javax.inject.Inject
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.info("Calling action")
    block(request)
  }

  override def composeAction[A](action: Action[A]): Action[A] = Logging[A](action)
}
