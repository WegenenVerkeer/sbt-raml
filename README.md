# sbt-raml - RAML tools support from within SBT

This SBT plugin enables you to:
- produce JAX-RS annotated Java classes from your [RAML](http://raml.org) files
- generate HTML documentation from your RAML files

## Usage

Add

    addSbtPlugin("be.vlaanderen.awv" % "sbt-raml" % "0.1.0-SNAPSHOT")

to your ``project/plugins.sbt`` and

 ```scala
ramlSettings
```

to your ``build.sbt`` or if you are using full configuration (with `Build.scala`):

```scala
.settings(sbtraml.RamlPlugin.ramlSettings: _*)
```

And configure the base package name used for generated Java classes:

```scala
ramlBasePackageName := "your.company.your.project"
```

## Tasks

TODO

## Settings

TODO

## License

Copyright (c) 2014 WegenenVerkeer

Published under the [The MIT License (MIT)](http://opensource.org/licenses/MIT).