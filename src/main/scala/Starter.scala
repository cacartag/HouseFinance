import Starter.{browser, scrapper}
import actors.Guardian
import akka.NotUsed
import akka.actor.typed.{ActorSystem, Scheduler}
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.serialization.SerializationExtension
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.Timeout
import flows.TruliaFlows.{basicHomeExtract, createTruliaURL, getPage}
import models.{DownPayment, FinanceMessage, TalkFinance, TruliaListing}

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.DurationInt
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._

import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import scala.annotation.tailrec
import scala.util.Try

//import scala.io.{Source, StdIn}

object Starter extends App {

  implicit val system: ActorSystem[FinanceMessage] = ActorSystem(Guardian.apply, "FinanceSystem")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  implicit val timer: Timeout = Timeout(5.seconds)
  implicit val scheduler: Scheduler = system.scheduler
  val j = Source(Set(1,2,3))

  WebDriverManager.chromedriver().setup()

//  val options = new ChromeOptions()
//  options.addArguments("--headless")

//  val driver = new ChromeDriver()

  val truliaInput = scala.io.Source.fromResource("samplehtml/TruliaSite.txt").mkString

//  driver.get("https://www.realtor.com/realestateandhomes-search/New-York_NY")
//  val baseUrl = "https://www.trulia.com"
//  driver.get(s"$baseUrl/AR/Springdale/")
//  driver.get("https://yahoo.com")

//  val page: String = driver.getPageSource

  val browser = JsoupBrowser()
  val scrapper = browser.parseString(truliaInput)

  // pull data for individual homes in the page
  val homesList: Option[List[Element]] = scrapper >?> elementList("div[data-testid=\"property-card-details\"]")

//  This will pull out data for a single page of results
//  Source(homesList.getOrElse(Nil))
//    .via(basicHomeExtract)
//    .runWith(Sink.foreach(println))


//  pull index of last page of result for
  val pagination = (scrapper >?> element("nav[data-testid=\"search-results-pagination\"]") >?> elementList("li") >?> attr("href")("a")).flatten.head.filter(_.isDefined).map(_.get).max
  val lastPageIndex = pagination.split("/").last.split("_").head


//  val truliaGraph = Source(homesList.get)
//    .via(basicHomeExtract)
//
//

  import akka.http.scaladsl.server.Directives
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
  import spray.json._


  val datePattern = "MM_dd_yyyy_KK_mm_aa"
  val simpleFormat = new SimpleDateFormat(datePattern)
  val date: String = simpleFormat.format(new Date())

  val g = TruliaListing(Some("$680"), Some("4bd"),Some("4ba"),Some("3,276 sqft (on 0.50 acres"),Some("255 Edinburgh"),Some("Springdale"),Some("AR"),Some("72762"),Some("/p/ar/springdale/1255-edinburgh-loop-springdale-ar-72762--2064726008"))

  val serialization = SerializationExtension(system)

  println(serialization.serialize(g))

  val writeDirectory = "C:\\Users\\chris\\Documents\\TruliaData\\"
  val fileName = s"TruliaData_$date.txt"
  val pw = new BufferedWriter(new FileWriter(s"$writeDirectory$fileName"))
  pw.write("Hello World to the file")
  pw.close()



//    .runWith(Sink.foreach(println))

//  driver.close()

//  Source(createTruliaURL(lastPageIndex.toInt))
//    .via(getPage(baseUrl))
//    .via(basicHomeExtract)
//    .runWith(Sink.foreach(println))


}






