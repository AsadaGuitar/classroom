package com.github.AsadaGuitar.classroom.controller.protocol

final case class StudentsListResponseJson(students: List[StudentResponseJson],
                                          totalCount: Long)