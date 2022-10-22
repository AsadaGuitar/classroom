package com.github.AsadaGuitar.classroom.controller.protocol


import com.github.AsadaGuitar.classroom.controller.protocol.FindStudentsByTeachersIdRequestParameters.SortKey.sortKeys
import com.github.AsadaGuitar.classroom.controller.protocol.FindStudentsByTeachersIdRequestParameters.{LikeParameter, SortKey}
import com.github.AsadaGuitar.classroom.controller.util.{Pagination, SortBy}
import com.github.AsadaGuitar.classroom.domain.Teacher

import scala.util.matching.Regex

final case class FindStudentsByTeachersIdRequestParameters(facilitatorId: Teacher.Id,
                                                           pagination: Option[Pagination],
                                                           sort: Option[SortBy[SortKey]],
                                                           like: Option[LikeParameter])

object FindStudentsByTeachersIdRequestParameters {

  final case class SortKey(value: String) {
    require(sortKeys.exists(_.equals(value)))
  }

  object SortKey {
    private val sortKeys = Vector("id", "name", "loginId")
  }

  final class LikeParameter(_key: String, val value: String) {
    import LikeParameter._

    val key: String = _key match {
      case keyPattern(x) if studentColumns.contains(x) => x
      case _ => throw new IllegalArgumentException("Invalid argument.")
    }
  }

  object LikeParameter {
    private val studentColumns = Seq("name", "loginId")
    private val keyPattern = "(.+)_like".r

    val likePattern: Regex = "^.+_like$".r
  }
}