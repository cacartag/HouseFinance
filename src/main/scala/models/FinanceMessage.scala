package models

import akka.actor.typed.ActorRef

sealed trait FinanceMessage

case class DownPayment(sender: ActorRef[FinanceMessage], down: BigDecimal) extends FinanceMessage

case class MonthlyPayment(monthly: BigDecimal) extends FinanceMessage
case class TalkFinance(reply: String) extends FinanceMessage


case class TruliaListing(price: String,
                         beds: String,
                         baths: String,
                         sqFt: String,
                         street: String,
                         city: String,
                         zipCode: String
                        ) extends FinanceMessage







