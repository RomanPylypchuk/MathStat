name := "MathStat"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "1.2",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.2",

  "com.github.haifengl" %% "smile-scala" % "2.6.0",

  "org.apache.commons" % "commons-math3" % "3.3"
)
