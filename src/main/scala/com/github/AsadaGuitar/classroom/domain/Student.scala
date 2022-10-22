package com.github.AsadaGuitar.classroom.domain

import java.time.Instant

final case class Student(id: Student.Id,
                         name: Student.Name,
                         loginId: Student.LoginId,
                         close: Boolean,
                         createdAt: Instant,
                         modifiedAt: Option[Instant],
                         closedAt: Option[Instant]) extends ValueObject

object Student {

  final case class Id(value: Int) extends ValueObject {
    require(0 < value)
  }

  final case class Name(value: String) extends ValueObject {
    require(value.nonEmpty)
  }

  final case class LoginId(value: String) extends ValueObject {
    require(value.nonEmpty)
  }
}