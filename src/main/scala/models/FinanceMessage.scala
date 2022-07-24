package models

import akka.actor.typed.ActorRef

sealed trait FinanceMessage

case class DownPayment(sender: ActorRef[FinanceMessage], down: BigDecimal) extends FinanceMessage

case class MonthlyPayment(monthly: BigDecimal) extends FinanceMessage
case class TalkFinance(reply: String) extends FinanceMessage


case class TruliaListing(price: Option[String],
                         beds: Option[String],
                         baths: Option[String],
                         sqFt: Option[String],
                         street: Option[String],
                         city: Option[String],
                         state: Option[String],
                         zipCode: Option[String],
                         link: Option[String]
                        ) extends FinanceMessage







