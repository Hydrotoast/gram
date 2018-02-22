// Global settings
name := "tensor"
organization := "com.giocc"
scalaVersion := "2.11.8"
crossScalaVersions := Vector("2.10.6", "2.11.8", "2.12.4")

enablePlugins(GitVersioning)
git.baseVersion := "0.0.1"

lazy val root = project
  .in(file("."))
  .aggregate(
    core
  )

lazy val core = project
