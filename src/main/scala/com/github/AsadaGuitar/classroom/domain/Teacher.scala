package com.github.AsadaGuitar.classroom.domain

import java.time.Instant

final case class Teacher(id: Teacher.Id,
                         name: Teacher.Name,
                         loginId: Teacher.LoginId,
                         close: Boolean,
                         createdAt: Instant,
                         modifiedAt: Option[Instant],
                         closedAt: Option[Instant])

object Teacher {

  final case class Id(value: Int) extends ValueObject

  final case class Name(value: String) extends ValueObject {
    require(value.nonEmpty)
  }

  final case class LoginId(value: String) extends ValueObject {
    require(value.nonEmpty)
  }
}