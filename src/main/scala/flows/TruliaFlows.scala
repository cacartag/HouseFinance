package flows

import akka.NotUsed
import akka.stream.scaladsl.Flow
import io.github.bonigarcia.wdm.WebDriverManager
import models.TruliaListing
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{attr, elementList, text}
import net.ruippeixotog.scalascraper.dsl.DSL._
import org.openqa.selenium.chrome.ChromeDriver
import scala.annotation.tailrec
import scala.util.Try

object TruliaFlows {

  @tailrec
  def createTruliaURL(current: Int, urlList: List[String] = Nil): List[String] = {
    if (current == 0)
      urlList
    else
      createTruliaURL(current - 1, urlList :+ s"/AR/Springdale/${current}_p/")
  }

  def basicHomeExtract: Flow[Element, Product, NotUsed] = Flow[Element].map {
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

  def getPage(baseUrl: String): Flow[String, Element, NotUsed] = Flow[String].mapConcat{
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

}
