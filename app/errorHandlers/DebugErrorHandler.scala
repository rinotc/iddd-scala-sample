package errorHandlers

import play.api.Logging
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

class DebugErrorHandler extends HttpErrorHandler with Logging {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)("client error occurred")
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val stackTrace = exception.getStackTrace.map(_.toString).toSeq
    val errorResponse = Json.obj(
      "code"       -> "server.error",
      "message"    -> s"A server error occurred: ${exception.getMessage}",
      "stackTrace" -> stackTrace
    )

    logger.error(exception.getMessage, exception)
    Future.successful(
      InternalServerError(errorResponse)
    )
  }
}
