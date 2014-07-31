credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo <<= version { (v: String) =>
  val nexus = "http://dev-colab.awv.vlaanderen.be/nexus/content/repositories/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("dev-colab snapshots" at nexus + "snapshots")
  else
    Some("dev-colab releases"  at nexus + "releases")
}

publishMavenStyle := true

publishArtifact in Test := false
