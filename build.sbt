sbtPlugin := true

name := "sbt-raml"

organization := "be.vlaanderen.awv"

libraryDependencies ++= Seq(
  "org.raml" % "raml-jaxrs-codegen-core" % "1.0-SNAPSHOT"
)

crossBuildingSettings

CrossBuilding.crossSbtVersions := Seq("0.12", "0.13")
