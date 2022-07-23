import Starter.{browser, scrapper}
import actors.Guardian
import akka.NotUsed
import akka.actor.typed.{ActorSystem, Scheduler}
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.Timeout
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

import scala.annotation.tailrec
import scala.util.Try

//import scala.io.{Source, StdIn}

object Starter extends App {

  implicit val system: ActorSystem[FinanceMessage] = ActorSystem(Guardian.apply, "FinanceSystem")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  implicit val timer: Timeout = Timeout(5.seconds)
  implicit val scheduler: Scheduler = system.scheduler

  println("Hello World!!!")

  val j = Source(Set(1,2,3))

  WebDriverManager.chromedriver().setup()

  val options = new ChromeOptions()
  options.addArguments("--headless")

  val driver = new ChromeDriver()

//  val truliaInput = scala.io.Source.fromResource("samplehtml/TruliaSite.txt").mkString

//  driver.get("https://www.realtor.com/realestateandhomes-search/New-York_NY")
  val baseUrl = "https://www.trulia.com"
  driver.get(s"$baseUrl/AR/Springdale/")
//  driver.get("https://yahoo.com")

//  val page: String = driver.getPageSource

  val browser = JsoupBrowser()
  val scrapper = browser.parseString(driver.getPageSource)

  // pull data for individual homes in the page
  val homesList: Option[List[Element]] = scrapper >?> elementList("div[data-testid=\"property-card-details\"]")

  val pullMoreData = Flow[Element].map {
    el =>
      (scrapper >?> element("nav[data-testid=\"search-results-pagination\"]") >> elementList("li") >> attr("href")("a")).get.toSet
  }

  // This will pull out data for a single page of results
  val basicHomeExtract: Flow[Element, Product, NotUsed] = Flow[Element].map {
    element =>
      val price = element >?> text("div[data-testid=\"property-price\"]")
      val beds = element >?> text("div[data-testid=\"property-beds\"]")
      val baths = element >?> text("div[data-testid=\"property-baths\"]")
      val sqFt = element >?> text("div[data-testid=\"property-floorSpace\"]")
      val addressCompact = element >?> attr("title")("div[data-testid=\"property-address\"]")


      // Further parsing of address
      if (addressCompact.isDefined) {
        Try {
          val splitAddress: Option[Array[String]] = Some(addressCompact.get.split(","))
          val street = Some(splitAddress.get.head.split(" ")
            .take(splitAddress.get.head.split(" ").length - 2).mkString(" "))
          val city = Some(splitAddress.get.head.split(" ").takeRight(1).head)
          val state = Some(splitAddress.get.last.split(" ").tail.head)
          val zipCode = Some(splitAddress.get.last.split(" ").tail.last)

          TruliaListing(price, beds, baths, sqFt, street, city, state,zipCode)
        }
      } else {
        TruliaListing(price, beds, baths, sqFt, None, None, None, None)
      }
  }

//  Source(homesList.getOrElse(Nil))
//    .via(basicHomeExtract)
//    .runWith(Sink.foreach(println))



//  pull data for pagination to go to all

  val getPage: Flow[String, Element, NotUsed] = Flow[String].mapConcat{
    site =>
      WebDriverManager.chromedriver().setup()
      val driver2 = new ChromeDriver()

      println("made it into stream")
      driver2.get(baseUrl+site)
      val browser = JsoupBrowser()
      val scrapper = browser.parseString(driver2.getPageSource)

      driver2.close()

      val resultSet = scrapper >?> elementList("div[data-testid=\"property-card-details\"]")

      if(resultSet.isDefined)
        resultSet.get
      else
        None
  }
  
  val pagination = (scrapper >?> element("nav[data-testid=\"search-results-pagination\"]") >?> elementList("li") >?> attr("href")("a")).flatten.head.filter(_.isDefined).map(_.get).max
  val lastPageIndex = pagination.split("/").last.split("_").head

  driver.close()

  @tailrec
  def createTruliaURL(current: Int, urlList: List[String] = Nil): List[String] = {
    if (current == 0)
      urlList
    else
      createTruliaURL(current - 1, urlList :+ s"/AR/Springdale/${current}_p/")
  }

  Source(createTruliaURL(lastPageIndex.toInt))
    .via(getPage)
    .via(basicHomeExtract)
    .runWith(Sink.foreach(println))

//  println(driver.getPageSource)
//  pagination.foreach(println)


//  println(driver.getPageSource)
//  println(homesList.get.head)
//  println(if (l.isDefined) l.get.foreach(println) else "Nothing")


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






