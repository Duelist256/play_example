package controllers.filters

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import javax.inject.Inject
import org.slf4j.LoggerFactory
import play.api.Logger
import play.api.libs.streams.Accumulator
import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader, Result}

import scala.concurrent.ExecutionContext

class AccumulatorFlowFilter @Inject()(actorSystem: ActorSystem)(implicit ec: ExecutionContext) extends EssentialFilter {

  private val logger = org.slf4j.LoggerFactory.getLogger("controllers.filters.AccumulatorFlowFilter")
  private implicit val logging: LoggingAdapter = Logging(actorSystem.eventStream, logger.getName)


  def apply(next: EssentialAction): EssentialAction = new EssentialAction {
    def apply(requestHeader: RequestHeader): Accumulator[ByteString, Result] = {
      val accumulator = next(requestHeader)

      val flow = Flow[ByteString].log("byteflow")
      val accumulatorWithResult = accumulator.through(flow).map { result =>
        logger.info(s"The flow has completed and the result is $result")
        result
      }
      accumulatorWithResult
    }
  }

}
