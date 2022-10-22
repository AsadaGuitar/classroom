package com.github.AsadaGuitar.classroom.domain.repository

import com.github.AsadaGuitar.classroom.domain.Student

import scala.concurrent.Future

trait StudentsRepository {

  /** 指定の生徒を返却 */
  def findById(id: Student.Id): Future[Option[Student]]
}