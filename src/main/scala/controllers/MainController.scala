package controllers

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import models.FinanceMessage

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

class MainController {


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

    println("Server online at http://localhost:8080")

    StdIn.readLine()
//
//    bindingFuture
//      .flatMap(_.unbind())
//      .onComplete(_ => system.terminate())
//
}
