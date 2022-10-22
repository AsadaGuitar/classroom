package com.github.AsadaGuitar.classroom.controller.validations

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.{provide, reject}
import com.github.AsadaGuitar.classroom.controller.protocol.FindStudentsByTeachersIdRequestParameters

trait ValidationDirectives {

  protected def validateFindStudentsByTeachersId(
    params: Seq[(String, String)]
  ): Directive1[FindStudentsByTeachersIdRequestParameters] =
    Validations.validateFindStudentsByTeachersId(params)
      .fold(e => reject(ValidationErrorRejection(e)), provide)
}