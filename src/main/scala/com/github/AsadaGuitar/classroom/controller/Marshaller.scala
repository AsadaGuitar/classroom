package com.github.AsadaGuitar.classroom.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.AsadaGuitar.classroom.controller.protocol.{ClassroomResponseJson, ErrorListResponseJson, ErrorResponseJson, StudentResponseJson, StudentsListResponseJson}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Marshaller extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val classroomResponseMarshaller: RootJsonFormat[ClassroomResponseJson] =
    jsonFormat2(ClassroomResponseJson)

  implicit val studentsResponseMarshaller: RootJsonFormat[StudentResponseJson] =
    jsonFormat4(StudentResponseJson)

  implicit val studentsListResponseMarshaller: RootJsonFormat[StudentsListResponseJson] =
    jsonFormat2(StudentsListResponseJson)

  implicit val errorResponseJsonMarshaller: RootJsonFormat[ErrorResponseJson] =
    jsonFormat1(ErrorResponseJson)

  implicit val errorListResponseJsonMarshaller: RootJsonFormat[ErrorListResponseJson] =
    jsonFormat1(ErrorListResponseJson)
}