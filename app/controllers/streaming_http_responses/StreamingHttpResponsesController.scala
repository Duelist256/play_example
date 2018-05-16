package controllers.streaming_http_responses

import java.io.{File, FileInputStream}

import akka.stream.scaladsl.{FileIO, Source, StreamConverters}
import akka.util.ByteString
import javax.inject.{Inject, Singleton}
import play.api.http.HttpEntity
import play.api.mvc.{BaseController, ControllerComponents, ResponseHeader, Result}

@Singleton
class StreamingHttpResponsesController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{

  def index = Action {
    Ok("Hello World")
  }

  def action = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world"), Some("text/plain"))
    )
  }

  def streamed = Action {

    val file = new File("tmp/when_is_one.pdf")
    val path = file.toPath
    val source = FileIO.fromPath(path)

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Streamed(source, None, Some("application/pdf"))
    )
  }

  def streamedWithContentLength = Action {

    val file = new File("tmp/when_is_one.pdf")
    val path = file.toPath
    val source = FileIO.fromPath(path)

    val contentLength = Some(file.length())

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Streamed(source, contentLength, Some("application/pdf"))
    )
  }

  def sendFile = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    Ok.sendFile(new java.io.File("tmp/parrot.gif"))
  }

  def sendFileWithName = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    Ok.sendFile(
      content = new java.io.File("tmp/parrot.gif"),
      fileName = _ => "party_parrot.gif"
    )
  }

  def sendFileAsAttachment = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    Ok.sendFile(
      content = new java.io.File("tmp/parrot.gif"),
      fileName = _ => "party_parrot.gif",
      inline = false
    )
  }

  private def getDataStream = new FileInputStream(new File("tmp/when_is_one.pdf"))

  def chunked = Action {
    val data = getDataStream
    val dataContent: Source[ByteString, _] = StreamConverters.fromInputStream(() => data)
    Ok.chunked(dataContent)
  }

  def chunkedFromSource = Action {
    val source = Source.apply(List("kiki", "foo", "bar"))
    Ok.chunked(source)
  }
}