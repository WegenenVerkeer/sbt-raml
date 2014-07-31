package sbtraml

import java.io.{FileInputStream, InputStreamReader}
import org.jsonschema2pojo.AnnotationStyle
import org.raml.jaxrs.codegen.core.Configuration.JaxrsVersion
import org.raml.jaxrs.codegen.core.Generator
import collection.JavaConversions._
import sbt._
import Keys._

object RamlPlugin extends Plugin {
  val ramlFilename = SettingKey[String]("raml-filename", "The name of the main RAML file.")
  val ramlSourceDirectory = SettingKey[File]("raml-source-directory", "Directory location of the RAML file(s).")

  val ramlBasePackageName = SettingKey[String]("raml-base-package-name", "Base package name used for generated Java classes.")
  val ramlJaxrsVersion = SettingKey[String]("raml-jaxrs-version", "The targeted JAX-RS version: either \"1.1\" or \"2.0\".")
  val ramlUseJsr303Annotations = SettingKey[Boolean]("raml-use-jsr303-annotations", "Should JSR-303 annotations be used?")
  val ramlJsonMapper = SettingKey[String]("raml-json-mapper", "The JSON object mapper to generate annotations to: either \"jackson1\", \"jackson2\" or \"gson\" or \"none\".")

  def generateSources(outputDirectory: File, sourceDirectory: File, ramlFile: File, basePackageName: String, jaxrsVersion: JaxrsVersion, useJsr303Annotations: Boolean, jsonMapper: AnnotationStyle): Seq[File] = {
    outputDirectory.mkdirs()
    val configuration = new org.raml.jaxrs.codegen.core.Configuration()
    configuration.setOutputDirectory(outputDirectory)
    configuration.setSourceDirectory(sourceDirectory)
    configuration.setBasePackageName(basePackageName)
    configuration.setJaxrsVersion(jaxrsVersion)
    configuration.setUseJsr303Annotations(useJsr303Annotations)
    configuration.setJsonMapper(jsonMapper)
    val ramlReader = new InputStreamReader(new FileInputStream(ramlFile))
    val files = new Generator().run(ramlReader, configuration)
    files map { file =>
      new File(outputDirectory, file)
    } toSeq
  }

  val ramlSettings = Seq(
    ramlFilename := "api.raml",
    ramlSourceDirectory := sourceDirectory.value / "main" / "raml"
  )

  val ramlJaxrsSettings = ramlSettings ++ Seq(
    ramlJaxrsVersion := "2.0",
    ramlJsonMapper := "jackson2",
    ramlUseJsr303Annotations := false,
    sourceGenerators in Compile <+= (Def.task { generateSources(
      ramlFile = ramlSourceDirectory.value / ramlFilename.value,
      outputDirectory = (sourceManaged in Compile).value / "raml-jaxrs",
      sourceDirectory = ramlSourceDirectory.value,
      basePackageName = ramlBasePackageName.value,
      jaxrsVersion = JaxrsVersion.fromAlias(ramlJaxrsVersion.value),
      useJsr303Annotations = ramlUseJsr303Annotations.value,
      jsonMapper = AnnotationStyle.valueOf(ramlJsonMapper.value.toUpperCase())
    )})
  )
}
