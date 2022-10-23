package com.github.AsadaGuitar.classroom.domain

import java.time.Instant

sealed trait Classroom {

  def id: Classroom.Id

  def name: Classroom.Name

  def teachers: Vector[Teacher.Id]

  def students: Vector[Student.Id]

  def close: Boolean

  def createdAt: Instant

  def modifiedAt: Option[Instant]

  def closedAt: Option[Instant]
}

object Classroom {

  final case class Id(value: Int) extends ValueObject

  final case class Name(value: String) extends ValueObject {
    require(value.nonEmpty)
  }

  final case class Impl(id: Classroom.Id,
                        name: Classroom.Name,
                        teachers: Vector[Teacher.Id],
                        students: Vector[Student.Id],
                        close: Boolean,
                        createdAt: Instant,
                        modifiedAt: Option[Instant],
                        closedAt: Option[Instant]) extends Classroom
}