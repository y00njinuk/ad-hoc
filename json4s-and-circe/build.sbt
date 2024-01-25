ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.13"

val circeVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "json4s-and-circe",

    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % "test",
      "org.json4s" %% "json4s-jackson" % "4.0.6",
      "com.thesamet.scalapb" %% "scalapb-json4s" % "0.12.0",
      "io.github.scalapb-json" %% "scalapb-circe" % "0.11.1",
    ),

    Compile / PB.targets := Seq(scalapb.gen() -> (Compile / sourceManaged).value / "scalapb")
  )
