lazy val rpg = project.in(file("."))
  .settings(moduleName := "rpg")
  .settings(buildSettings)
  .settings(publish := {}, publishLocal := {})
  .aggregate(gameJVM, gameJS)

lazy val browser = project
  .settings(moduleName := "browser")
  .settings(buildSettings)
  .settings(
    persistLauncher in Test := false,
    persistLauncher in Compile := true
  )
  .settings(libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.lihaoyi" %%% "scalatags" % "0.6.2"
  ))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(gameJS)

lazy val server = project
  .settings(moduleName := "rpg-server")
  .settings(buildSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.0"
    ),
    (resourceGenerators in Compile) <+=
      (fastOptJS in Compile in browser, packageScalaJSLauncher in Compile in browser)
        .map((f1, f2) => Seq(f1.data, f2.data))
  )
  .dependsOn(browser)

lazy val gameJVM = game.jvm
lazy val gameJS = game.js

lazy val game = crossProject
  .settings(moduleName := "game")
  .settings(buildSettings)
  .jvmSettings(libraryDependencies ++= Seq(
    "org.typelevel" %% "cats" % "0.8.1",
    "com.chuusai" %% "shapeless" % "2.3.2",

    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
  ))
  .jsSettings(libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats" % "0.8.1",
    "com.chuusai" %%% "shapeless" % "2.3.2",

    "org.scalatest" %%% "scalatest" % "3.0.0" % "test",
    "org.scalacheck" %%% "scalacheck" % "1.13.4" % "test"
  ))

lazy val buildSettings = Seq(
  organization := "io.github.rpless",
  version := "0.1.0",
  scalaVersion := "2.12.0",
  scalacOptions ++= compilerOptions
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
