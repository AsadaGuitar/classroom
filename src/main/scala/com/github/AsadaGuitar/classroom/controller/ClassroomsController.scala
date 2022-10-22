package com.github.AsadaGuitar.classroom.controller

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.AsadaGuitar.classroom.adaptor.postgres.{ClassroomsRepositoryImpl, StudentsRepositoryImpl}
import com.github.AsadaGuitar.classroom.controller.Implicits.RichStudentWithClassRoomsInfo
import com.github.AsadaGuitar.classroom.controller.protocol.FindStudentsByTeachersIdRequestParameters.SortKey
import com.github.AsadaGuitar.classroom.controller.protocol.{FindStudentsByTeachersIdRequestParameters, StudentResponseJson, StudentsListResponseJson}
import com.github.AsadaGuitar.classroom.controller.validations.ValidationDirectives
import com.github.AsadaGuitar.classroom.usecase.ClassroomsUseCase
import com.github.AsadaGuitar.classroom.util.Order.{ASC, DESC}

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

final class ClassroomsController(implicit system: ActorSystem[_]) extends Marshaller with ValidationDirectives {

  implicit val ec: ExecutionContextExecutor = system.executionContext

  private val classroomsRepository = new ClassroomsRepositoryImpl
  private val studentsRepository = new StudentsRepositoryImpl
  private val classroomsUseCase = new ClassroomsUseCase(classroomsRepository, studentsRepository)

  def findStudentsByTeacherId: Route = {

    type MoldStudents = Vector[ClassroomsUseCase.StudentWithClassroom] => Vector[ClassroomsUseCase.StudentWithClassroom]

    /** ライク検索 */
    def filterLike(implicit requestParams: FindStudentsByTeachersIdRequestParameters): Option[MoldStudents] =
      requestParams.like.map { l =>
        (students: Vector[ClassroomsUseCase.StudentWithClassroom]) =>
          /** 指定のキー値を対象にライク検索を実行 */
          l.key match {
            case "name" => students.filter(_.student.name.value.contains(l.value))
            case "loginId" => students.filter(_.student.loginId.value.contains(l.value))
            case _ => throw new RuntimeException
          }
      }

    /** ソート */
    def sortBy(implicit requestParams: FindStudentsByTeachersIdRequestParameters): Option[MoldStudents] =
      requestParams.sort.map { s =>
        (students: Vector[ClassroomsUseCase.StudentWithClassroom]) =>
          /** 指定のキー値を対象に昇順ソートを実行 */
          val sorted = s.key match {
            case SortKey("id") => students.sortBy(_.student.id.value)
            case SortKey("name") => students.sortBy(_.student.name.value)
            case SortKey("loginId") => students.sortBy(_.student.loginId.value)
            case _ => throw new RuntimeException
          }
          s.order match {
            case ASC => sorted
            case DESC => sorted.reverse
          }
      }

    /** ページネーション */
    def paginate(implicit requestParams: FindStudentsByTeachersIdRequestParameters): Option[MoldStudents] =
      requestParams.pagination.map { p =>
        (students: Vector[ClassroomsUseCase.StudentWithClassroom]) => p.paginate(students).toVector
      }

    /** 教師が担当する生徒一覧を取得 */
    path("students") {
      parameterSeq { params =>
        this.validateFindStudentsByTeachersId(params) { implicit requestParams =>
          onComplete(classroomsUseCase.findStudentsByTeachersId(requestParams.facilitatorId)) {
            case Success(students) =>
              try {
                /** ライク検索、ソート、ページネートを実行 */
                val molded: Vector[ClassroomsUseCase.StudentWithClassroom] =
                  Seq(filterLike, sortBy, paginate)
                    .flatten
                    .foldLeft(identity[Vector[ClassroomsUseCase.StudentWithClassroom]](_)) { (acc, x) =>
                      acc andThen x
                    }(students)
                /** 返却するJsonを作成 */
                val responseJson =
                  StudentsListResponseJson(
                    students = molded.map(_.convertToResponseJson).toList,
                    totalCount = molded.length)
                complete(responseJson)
              } catch {
                case e: IndexOutOfBoundsException =>
                  /** ページ取得時に例外が発生した場合 */
                  system.log.warn(e.getMessage)
                  val responseJson =
                    StudentsListResponseJson(
                      students = List.empty[StudentResponseJson],
                      totalCount = 0)
                  complete(responseJson)
                case e: Throwable =>
                  system.log.error(e.getMessage)
                  complete(InternalServerError)
              }
            case Failure(exception) =>
              /** データ取得失敗時 */
              system.log.error(exception.getMessage)
              complete(InternalServerError)
          }
        }
      }
    }
  }
}