name := "bl-readings-service"
version := "1.0"
scalaVersion := "2.13.1"
artifactId := "bl-readings-service"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
