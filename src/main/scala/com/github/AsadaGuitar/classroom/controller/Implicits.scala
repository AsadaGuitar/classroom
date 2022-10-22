package com.github.AsadaGuitar.classroom.controller

import com.github.AsadaGuitar.classroom.controller.protocol.{ClassroomResponseJson, StudentResponseJson}
import com.github.AsadaGuitar.classroom.usecase.ClassroomsUseCase

object Implicits {

  implicit class RichStudentWithClassRoomsInfo(target: ClassroomsUseCase.StudentWithClassroom) {

    def convertToResponseJson: StudentResponseJson ={
      StudentResponseJson(
        id = target.student.id.value,
        name = target.student.name.value,
        loginId = target.student.loginId.value,
        classroom =
          ClassroomResponseJson(
            id = target.classroom.id.value,
            name =target.classroom.name.value))
    }
  }
}