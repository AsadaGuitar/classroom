package com.github.AsadaGuitar.classroom

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.github.AsadaGuitar.classroom.controller.ClassroomsController
import com.github.AsadaGuitar.classroom.controller.validations.{RejectionHandlers, ValidationDirectives}
import com.typesafe.config.Config

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt
import scala.io.StdIn
import scala.util.{Failure, Success}

object Main extends App with ValidationDirectives {

  sealed trait Message
  private case class StartSucceeded(msg: String) extends Message
  private case class StartFailed(msg: String) extends Message

  def apply(): Behavior[Message] = Behaviors.setup[Message] { ctx =>
    implicit val system: ActorSystem[_] = ctx.system
    implicit val ec: ExecutionContext   = ctx.executionContext
    implicit val config: Config         = ctx.system.settings.config

    val host: String = config.getString("akka.http.server.host")
    val port: Int    = config.getInt("akka.http.server.port")

    val classRoomsController = new ClassroomsController
    val router =
      handleRejections(RejectionHandlers.defaultRejectionHandler) {
        classRoomsController.findStudentsByTeacherId
      }

    val bindingFuture: Future[Http.ServerBinding] =
      Http()
        .newServerAt(host, port)
        .bind(router)
        .map(_.addToCoordinatedShutdown(hardTerminationDeadline = 10.seconds))

    ctx.pipeToSelf(bindingFuture) {
      case Success(serverBinding) =>
        val address = serverBinding.localAddress
        StartSucceeded(s"server bound to http://${address.getHostString}:${address.getPort}")
      case Failure(ex) =>
        StartFailed(s"Failed to bind endpoint, terminating system: $ex")
    }

    Behaviors.receiveMessage {
      case StartSucceeded(msg) =>
        ctx.log.info(msg)
        Behaviors.same
      case StartFailed(msg) =>
        ctx.log.info(msg)
        Behaviors.stopped
    }
  }

  ActorSystem(Main(), "classroom")
  StdIn.readLine()
}