package com.github.AsadaGuitar.classroom.controller.validations

import cats.data.ValidatedNel
import cats.implicits.catsSyntaxValidatedId
import com.github.AsadaGuitar.classroom.controller.protocol.FindStudentsByTeachersIdRequestParameters.LikeParameter
import com.github.AsadaGuitar.classroom.controller.protocol.{ErrorResponseJson, FindStudentsByTeachersIdRequestParameters}
import com.github.AsadaGuitar.classroom.controller.util.{Pagination, SortBy}
import com.github.AsadaGuitar.classroom.domain.Teacher
import com.github.AsadaGuitar.classroom.util.Order

object Validations {

  def validateFindStudentsByTeachersId(
    params: Seq[(String, String)]
  ): ValidatedNel[ErrorResponseJson, FindStudentsByTeachersIdRequestParameters] ={
    if (params.isEmpty) {
      ErrorResponseJson("入力値が存在しません。").invalidNel
    } else {
      try {
        val teachersId = params.find(_._1.equals("facilitator_id")).flatMap(_._2.toIntOption.map(Teacher.Id)).get

        val page = params.find(_._1.equals("page")).flatMap(_._2.toIntOption)
        val limit = params.find(_._1.equals("limit")).flatMap(_._2.toIntOption)
        val paginate = for {
          p <- page
          l <- limit
        } yield Pagination(p, l)

        val sortKey = params.find(_._1.equals("sort")).map(p => FindStudentsByTeachersIdRequestParameters.SortKey(p._2))
        val order = params.find(_._1.equals("order")).flatMap {
          case (_, "asc") => Some(Order.ASC)
          case (_, "desc") => Some(Order.DESC)
          case _ => None
        }
        val sortBy = for {
          k <- sortKey
          o <- order
        } yield SortBy(k, o)

        val likeParameter =
          params.find(p => LikeParameter.likePattern.matches(p._1))
            .map(p => new FindStudentsByTeachersIdRequestParameters.LikeParameter(p._1, p._2))

        FindStudentsByTeachersIdRequestParameters(teachersId, paginate, sortBy, likeParameter).validNel
      } catch {
        case _: Throwable =>
          ErrorResponseJson("入力値が不正です。").invalidNel
      }
    }
  }
}