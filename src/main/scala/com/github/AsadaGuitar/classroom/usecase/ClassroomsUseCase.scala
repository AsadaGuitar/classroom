package com.github.AsadaGuitar.classroom.usecase

import com.github.AsadaGuitar.classroom.domain.{Classroom, Student, Teacher}
import com.github.AsadaGuitar.classroom.domain.repository.{ClassroomsRepository, StudentsRepository}

import scala.concurrent.{ExecutionContext, Future}

object ClassroomsUseCase {

  final case class ClassroomInfo(id: Classroom.Id, name: Classroom.Name)
  final case class StudentWithClassroom(student: Student, classroom: ClassroomInfo)
}

final class ClassroomsUseCase(classroomsRepository: ClassroomsRepository,
                              studentsRepository: StudentsRepository)(implicit ec: ExecutionContext) {
  import ClassroomsUseCase._

  /** 指定の教師が担当している生徒の一覧を返却 */
  def findStudentsByTeachersId(teachersId: Teacher.Id): Future[Vector[StudentWithClassroom]] ={
    for {
      /** 指定の教師が担当している教室を全件取得 */
      classrooms <- classroomsRepository.filterByTeachersId(teachersId)
      /** 各教室から生徒を重複なしで取得 */
      studentIdWithClassroom = classrooms.flatMap(_.students).distinct.map { id =>
        val classroomsInfo = classrooms.find(_.students.contains(id)).map { c =>
          ClassroomInfo(c.id, c.name)
        }.get
        (id, classroomsInfo)
      }
      students <- Future.sequence{
        studentIdWithClassroom.map { idWithInfo =>
          studentsRepository.findById(idWithInfo._1).map { student =>
            student.map { s =>
              StudentWithClassroom(s, idWithInfo._2)
            }
          }
        }
      }
    } yield students.flatten
  }
}