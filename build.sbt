name := "HouseFinance"

version := "0.1"

scalaVersion := "2.13.8"

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.2"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)



libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.36"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.36"
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "4.0.0"
libraryDependencies += "io.github.bonigarcia" % "webdrivermanager" % "5.2.1"
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "3.0.0"






