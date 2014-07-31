credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.2")

addSbtPlugin("net.virtual-void" % "sbt-cross-building" % "0.8.1")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")