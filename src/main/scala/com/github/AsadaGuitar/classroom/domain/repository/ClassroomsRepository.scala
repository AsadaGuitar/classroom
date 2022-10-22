package com.github.AsadaGuitar.classroom.domain.repository

import com.github.AsadaGuitar.classroom.domain.{Classroom, Teacher}

import scala.concurrent.Future

trait ClassroomsRepository {

  /** 指定の教師が担当する教室を返却 */
  def filterByTeachersId(teachersId: Teacher.Id): Future[Vector[Classroom]]
}