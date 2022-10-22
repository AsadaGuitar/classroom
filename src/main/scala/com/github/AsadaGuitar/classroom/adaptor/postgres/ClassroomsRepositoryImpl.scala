package com.github.AsadaGuitar.classroom.adaptor.postgres

import com.github.AsadaGuitar.classroom.adaptor.postgres.dao.Tables
import com.github.AsadaGuitar.classroom.domain.{Classroom, Student, Teacher}
import com.github.AsadaGuitar.classroom.domain.repository.ClassroomsRepository

import scala.concurrent.{ExecutionContext, Future}

class ClassroomsRepositoryImpl(implicit ec: ExecutionContext)
  extends ClassroomsRepository with DbConfigProvider {

  import Tables.profile.api._
  import Implicits.RichClassroomsRow

  override def filterByTeachersId(teachersId: Teacher.Id): Future[Vector[Classroom]] =
    dbConfig.db.run {
      Tables.TeacherClassrooms
        .join(Tables.Classrooms).on(_.classroomId === _.id)
        .join(Tables.TeacherClassrooms).on(_._2.id === _.classroomId)
        .join(Tables.Teachers).on(_._2.teacherId === _.id)
        .join(Tables.Students).on(_._1._1._2.id === _.classroomId)
        .filter(_._1._1._1._1.teacherId === teachersId.value)
        .filter(!_._1._1._1._1.close)
        .filter(!_._1._1._1._2.close)
        .filter(!_._1._1._2.close)
        .filter(!_._1._2.close)
        .filter(!_._2.close)
        .sortBy(_._1._1._2.id)
        .map { case ((((_, c), _), t), s) => (c, t, s) }
        .result
        .map { rows =>
          rows.foldLeft(Vector.empty[Classroom.Impl]) {
            case (acc, (c, t, s)) =>
              acc.find(_.id.value == c.id) match {
                case Some(classroom) =>
                  acc.filterNot(_.id.value == c.id) :+
                    classroom.copy(
                      teachers = classroom.teachers :+ Teacher.Id(t.id),
                      students = classroom.students :+ Student.Id(s.id))
                case None =>
                  val classroom =
                    c.convertToClassroomsImpl.copy(
                      teachers = Vector(Teacher.Id(t.id)),
                      students = Vector(Student.Id(s.id)))
                  acc :+ classroom
              }
          }
        }
    }
}