package com.github.AsadaGuitar.classroom.util

sealed trait Order

object Order {

  case object ASC extends Order

  case object DESC extends Order
}