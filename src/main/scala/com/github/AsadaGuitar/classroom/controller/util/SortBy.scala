package com.github.AsadaGuitar.classroom.controller.util

import com.github.AsadaGuitar.classroom.util.Order

final case class SortBy[A](key: A, order: Order)