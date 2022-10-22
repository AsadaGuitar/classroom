package com.github.AsadaGuitar.classroom.controller.protocol

final case class StudentResponseJson(id: Int,
                                     name: String,
                                     loginId: String,
                                     classroom: ClassroomResponseJson)