package controllers

import java.io.File

import akka.stream.scaladsl.{Flow, Framing, Keep, Sink}
import akka.util.ByteString
import javax.inject._
import play.api.libs.json.JsValue
import play.api.libs.streams.Accumulator
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CustomBodyParser @Inject()(ws: WSClient, val controllerComponents: ControllerComponents)
                                (implicit ec: ExecutionContext) extends BaseController {

  def forward(request: WSRequest): BodyParser[WSResponse] = BodyParser { req =>
    Accumulator.source[ByteString].mapFuture { source =>
      request
        .withBody(source)
        .execute("POST")
        .map(Right.apply)
    }
  }

  def useParser: Action[WSResponse] = Action(forward(ws.url("http://localhost:9000/bodyParserExamples/saveToFile"))) { req =>
    Ok("Uploaded")
  }
}