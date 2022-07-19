import Starter.browser
import actors.Guardian
import akka.NotUsed
import akka.actor.typed.{ActorSystem, Scheduler}
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.Timeout
import models.{DownPayment, FinanceMessage, TalkFinance}

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.DurationInt
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._

//import scala.io.{Source, StdIn}



object Starter extends App {

  println("Hello World!!!")

  val j = Source(Set(1,2,3))

//  WebDriverManager.chromedriver().setup()
//
//
//  val options = new ChromeOptions()
//  options.addArguments("--headless")
//
//  val driver = new ChromeDriver()

  val truliaInput = scala.io.Source.fromResource("samplehtml/TruliaSite.txt").mkString

//  driver.get("https://www.realtor.com/realestateandhomes-search/New-York_NY")
//  driver.get("https://www.trulia.com/AR/Springdale/")
//  driver.get("https://yahoo.com")

//  val page: String = driver.getPageSource

  val browser = JsoupBrowser()
  val scrapper = browser.parseString(truliaInput)

  // pull data for individual homes in overview
  val l = scrapper >?>  elementList("div[data-testid=\"property-card-details\"]")

  val price = l.get.head >?> text("div[data-testid=\"property-price\"]")
  val beds = l.get.head >?> text("div[data-testid=\"property-beds\"]")
  val baths = l.get.head >?> text("div[data-testid=\"property-baths\"]")
  val sqFt = l.get.head >?> text("div[data-testid=\"property-floorSpace\"]")
  val addressCompact = l.get.head >?> attr("title")("div[data-testid=\"property-address\"]")

  // pull data for pagination to go to all
  // val pagination = (scrapper >?> element("nav[data-testid=\"search-results-pagination\"]") >> elementList("li") >> attr("href")("a")).get.toSet



//  println(driver.getPageSource)
  println(l.get.head)
  println(price, beds, baths, sqFt, addressCompact)
//  println(if (l.isDefined) l.get.foreach(println) else "Nothing")

//  implicit val system: ActorSystem[FinanceMessage] = ActorSystem(Guardian.apply, "FinanceSystem")
//  implicit val executionContext: ExecutionContextExecutor = system.executionContext
//  implicit val timer: Timeout = Timeout(5.seconds)
//  implicit val scheduler: Scheduler = system.scheduler
//
//
//  val flowIncrement: Flow[Int, Int, NotUsed] = Flow[Int].map(in => in * 5)
//
//  j.via(flowIncrement).runWith(Sink.foreach(println))


//
//  val route =
//    concat(
//      path("DownPayed.html"){
//        get {
//          parameters("DownAmount") { downAmount =>
//            val accountReply: FinanceMessage = Await.result(system ? (ret => DownPayment(ret, BigDecimal(downAmount))),5.seconds)
//
//            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Accountant response  ${accountReply.asInstanceOf[TalkFinance].reply} </h1>"))
//          }
//        }
//      },
//      path("Home"){
//        get {
//          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, Source.fromResource("html/Index.html").mkString))
//        }
//      }
//    )
//
//
//
//  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
//
//  println("Server online at http://localhost:8080")
//
//  StdIn.readLine()
//
//  bindingFuture
//    .flatMap(_.unbind())
//    .onComplete(_ => system.terminate())


}






