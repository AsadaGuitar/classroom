val catsCoreVersion = "2.8.0"
val akkaVersion = "2.6.19"
val akkaHttpVersion = "10.2.9"
val slickVersion = "3.3.3"
val logbackVersion = "1.2.11"
val postgresVersion = "42.3.6"
val scalaTestVersion = "3.2.12"

lazy val baseSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.8",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-encoding",
    "UTF-8",
    "-language:_",
    "-Ydelambdafy:method",
    "-target:jvm-1.8",
    "-Yrangepos",
    "-Ywarn-unused"
  ),
  semanticdbEnabled := true,
  Test / publishArtifact := false,
  Test / fork := true,
  Test / parallelExecution := false
)

lazy val root = (project in file("."))
  .settings(baseSettings)
  .settings(
    name := "classroom",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % catsCoreVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "org.postgresql" % "postgresql" % postgresVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    )
  )