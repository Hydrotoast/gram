// Common settings
lazy val commonSettings = Seq(
  organization := "com.giocc",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.8"))

// Git versioning
lazy val versionSettings = Seq(git.baseVersion := "0.0.1")

// Bintray
lazy val publishSettings = Seq(
  licenses += "MIT" -> url("http://opensource.org/licenses/MIT"),
  bintrayPackage := name.value,
  bintrayPackageLabels := Seq("scala", "tensor"),
  bintrayRepository := "gram",
  publishArtifact := true,
  publishArtifact in Test := false,
  publishMavenStyle := true
)

lazy val disablePublishSettings = Seq(publish := {})

lazy val root = project
  .in(file("."))
  .enablePlugins(GitVersioning)
  .settings(
    commonSettings ++
      disablePublishSettings ++
      Seq(name := "gram"))
  .aggregate(tensorCore, tensorOperations, tensorViews)

lazy val tensorCore = project
  .in(file("tensor-core"))
  .settings(
    commonSettings ++
      versionSettings ++
      publishSettings ++
      Seq(name := "tensor-core"))

lazy val tensorViews = project
  .in(file("tensor-views"))
  .dependsOn(tensorCore)
  .settings(
    commonSettings ++
      versionSettings ++
      publishSettings ++
      Seq(name := "tensor-views"))

lazy val tensorOperations = project
  .in(file("tensor-operations"))
  .dependsOn(tensorCore)
  .settings(
    commonSettings ++
      versionSettings ++
      publishSettings ++
      Seq(name := "tensor-operations"))
