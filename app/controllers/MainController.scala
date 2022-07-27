package controllers

import models.FinanceMessage
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.PostgresProfile

import javax.inject.Inject
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

class MainController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
  extends AbstractController(cc)
  with HasDatabaseConfigProvider[PostgresProfile]
  {

    def starting = Action {



        Ok("Welcome to this very first page!!!!")
    }

//    val route =
//      concat(
//        path("DownPayed.html"){
//          get {
//            parameters("DownAmount") { downAmount =>
//              val accountReply: FinanceMessage = Await.result(system ? (ret => DownPayment(ret, BigDecimal(downAmount))),5.seconds)
//
//              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Accountant response  ${accountReply.asInstanceOf[TalkFinance].reply} </h1>"))
//            }
//          }
//        },
//        path("Home"){
//          get {
//            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, Source.fromResource("html/Index.html").mkString))
//          }
//        }
//      )



//    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

//    println("Server online at http://localhost:8080")
//
//    StdIn.readLine()
//
//    bindingFuture
//      .flatMap(_.unbind())
//      .onComplete(_ => system.terminate())
//
}
