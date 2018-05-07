package controllers.filters

import akka.util.ByteString
import javax.inject.Inject
import play.api.Logger
import play.api.libs.streams.Accumulator
import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader, Result}

import scala.concurrent.ExecutionContext

class EssentialLoggingFilter @Inject() (implicit ec: ExecutionContext) extends EssentialFilter {
  def apply(nextFilter: EssentialAction): EssentialAction = new EssentialAction {
    def apply(requestHeader: RequestHeader): Accumulator[ByteString, Result] = {
      val startTime = System.currentTimeMillis()
      val accumulator = nextFilter(requestHeader)

      accumulator.map { result =>
        val endTime = System.currentTimeMillis()
        val requestTime = endTime - startTime

        Logger.info(s"EF: ${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
        result.withHeaders("Request-Time" -> requestTime.toString)
      }
    }
  }
}
