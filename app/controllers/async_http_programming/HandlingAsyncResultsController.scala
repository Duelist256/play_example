package controllers.async_http_programming

import javax.inject._
import play.api.Logger
import play.api.libs.concurrent.Futures
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import play.api.libs.concurrent.Futures._

@Singleton
class HandlingAsyncResultsController @Inject()(val controllerComponents: ControllerComponents)(implicit futures: Futures) extends BaseController {

  private def intensiveComputation = {
    Future {
      Thread.sleep(999)
      1
    }
  }

  def index: Action[AnyContent] = Action.async {
    val futureInt = intensiveComputation
    Logger.info(s"In play")
    futureInt.map(i => Ok("Got result: " + i))
  }

  def handlingTimeout: Action[AnyContent] = Action.async {
    intensiveComputation.withTimeout(1.seconds).map { i =>
      Ok("Got result: " + i)
    }.recover {
      case e: TimeoutException => InternalServerError("timeout")
    }
  }
}