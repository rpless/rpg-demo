lazy val rpg = project.in(file("."))
  .settings(moduleName := "rpg")
  .settings(buildSettings)
  .settings(publish := {}, publishLocal := {})
  .aggregate(gameJVM, gameJS)

lazy val webserver = project
  .settings(moduleName := "webserver")
  .settings(buildSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.4",
      "io.circe" %% "circe-core" % "0.7.0",
      "io.circe" %% "circe-generic" % "0.7.0",
      "io.circe" %% "circe-parser" % "0.7.0"
    ),
    (resourceGenerators in Compile) <+=
      (fastOptJS in Compile in browser, packageScalaJSLauncher in Compile in browser)
        .map((f1, f2) => Seq(f1.data, f2.data))
  )
  .dependsOn(distributed)

lazy val browser = project
  .settings(moduleName := "browser")
  .settings(buildSettings)
  .settings(
    persistLauncher in Test := false,
    persistLauncher in Compile := true
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.lihaoyi" %%% "scalatags" % "0.6.2",
      "io.circe" %%% "circe-scalajs" % "0.7.0",
      "io.circe" %%% "circe-generic" % "0.7.0",
      "io.circe" %%% "circe-parser" % "0.7.0"
    )
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(gameJS)

lazy val distributed = project
  .settings(moduleName := "distributed")
  .settings(buildSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.17",
      "com.typesafe.akka" %% "akka-stream" % "2.4.17"
    )
  )
  .dependsOn(gameJVM)

lazy val gameJVM = game.jvm
lazy val gameJS = game.js

lazy val game = crossProject
  .settings(moduleName := "game")
  .settings(buildSettings)
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "com.chuusai" %% "shapeless" % "2.3.2",

      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats" % "0.9.0",
      "com.chuusai" %%% "shapeless" % "2.3.2",

      "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
      "org.scalacheck" %%% "scalacheck" % "1.13.4" % "test"
    )
  )

lazy val buildSettings = Seq(
  organization := "io.github.rpless",
  version := "0.1.0",
  scalaVersion := "2.12.0",
  scalacOptions ++= compilerOptions,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

lazy val compilerOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Xlint"
)
