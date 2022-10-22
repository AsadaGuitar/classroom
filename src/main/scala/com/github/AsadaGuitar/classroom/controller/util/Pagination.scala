package com.github.AsadaGuitar.classroom.controller.util

final case class Pagination(page: Int, limit: Int) {

  @throws[IndexOutOfBoundsException]
  def paginate[A](l: Seq[A]): Seq[A] = l.sliding(limit).toList(page)
}