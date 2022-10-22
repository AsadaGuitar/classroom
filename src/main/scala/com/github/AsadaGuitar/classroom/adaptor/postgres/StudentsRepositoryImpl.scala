package com.github.AsadaGuitar.classroom.adaptor.postgres


import com.github.AsadaGuitar.classroom.adaptor.postgres.dao.Tables
import com.github.AsadaGuitar.classroom.domain.Student
import com.github.AsadaGuitar.classroom.domain.repository.StudentsRepository

import scala.concurrent.{ExecutionContext, Future}

class StudentsRepositoryImpl(implicit ec: ExecutionContext) extends StudentsRepository with DbConfigProvider {

  import Tables.profile.api._
  import Implicits.RichStudentsRow

  override def findById(id: Student.Id): Future[Option[Student]] =
    dbConfig.db.run {
      Tables.Students.filter(_.id === id.value).result.headOption
    }.map(_.map(_.convertToStudent))
}