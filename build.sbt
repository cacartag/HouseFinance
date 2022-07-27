name := "HouseFinance"

version := "0.1"

scalaVersion := "2.13.8"

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.2"
lazy val HouseFinanceApplication = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)



//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
//  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
//)

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "4.0.0"
libraryDependencies += "io.github.bonigarcia" % "webdrivermanager" % "5.2.1"
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "3.0.0"

libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.3.2"



//libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.36"
//libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.36"

// play library imports
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
//  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
)

libraryDependencies += "org.postgresql" % "postgresql" % "42.4.0"


lazy val hello = taskKey[Unit]("Prints 'Hello World'")


hello := println(s"Source stuff ${(Compile / sourceDirectory).value} ")






lazy val slick = taskKey[Seq[File]]("generates All Tables from PostgreSQL")
slick := {
  val dir = (Compile / sourceDirectory).value
  val url = "jdbc:postgresql://192.168.50.3:5420/cacartag?user=cacartag&password=alexis"
  val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "slick.jdbc.PostgresProfile"
  val pkg = "schemas"

  val cp = (Compile / dependencyClasspath).value
  val s = streams.value


  println("running the slick code generator")
  // this is so fucking cool
  runner.value.run("slick.codegen.SourceCodeGenerator",
    cp.files,
    Array(slickDriver, jdbcDriver, url, dir.getPath, pkg),
    s.log).failed foreach (sys error _.getMessage)

  val file = dir / pkg / "Tables.scala"

  Seq(file)
}

(Compile / sourceGenerators) += slick.taskValue





