package com.github.AsadaGuitar.classroom.adaptor.postgres

import com.github.AsadaGuitar.classroom.adaptor.postgres.dao.Tables
import com.github.AsadaGuitar.classroom.domain.{Classroom, Student}

object Implicits {

  implicit class RichClassroomsRow(row: Tables.ClassroomsRow) {
    def convertToClassroomsImpl: Classroom.Impl =
      Classroom.Impl(
        id = Classroom.Id(row.id),
        name = Classroom.Name(row.name),
        teachers = Vector.empty, 
        students = Vector.empty,
        close = row.close,
        createdAt = row.createdAt.toInstant, 
        modifiedAt = row.modifiedAt.map(_.toInstant), 
        closedAt = row.closedAt.map(_.toInstant))
  }

  implicit class RichStudentsRow(row: Tables.StudentsRow) {
    def convertToStudent: Student =
      Student(
        id = Student.Id(row.id),
        name = Student.Name(row.name),
        loginId = Student.LoginId(row.loginId),
        close = row.close,
        createdAt = row.createdAt.toInstant,
        modifiedAt = row.modifiedAt.map(_.toInstant),
        closedAt = row.modifiedAt.map(_.toInstant))
  }
}