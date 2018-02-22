// Global settings
name := "tensor"
organization := "com.giocc"
scalaVersion := "2.11.8"

lazy val root = project
  .in(file("."))
  .aggregate(
    core
  )

lazy val core = project.in(file("core"))
