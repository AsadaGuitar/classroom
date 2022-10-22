package com.github.AsadaGuitar.classroom.controller.validations

import akka.http.javadsl.server.CustomRejection
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.RejectionHandler
import cats.data.NonEmptyList
import com.github.AsadaGuitar.classroom.controller.Marshaller
import com.github.AsadaGuitar.classroom.controller.protocol.{ErrorListResponseJson, ErrorResponseJson}

object RejectionHandlers extends Marshaller {
  final val defaultRejectionHandler: RejectionHandler = RejectionHandler
    .newBuilder()
    .handle { case ValidationErrorRejection(errors) =>
      complete(
        StatusCodes.BadRequest,
        ErrorListResponseJson(errors.toList))
    }
    .result()
}

final case class ValidationErrorRejection(errors: NonEmptyList[ErrorResponseJson]) extends CustomRejection