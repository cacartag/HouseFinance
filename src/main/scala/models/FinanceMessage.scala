package models

import Serializable.CustomSerialize
import akka.actor.typed.ActorRef

sealed trait FinanceMessage

case class DownPayment(sender: ActorRef[FinanceMessage], down: BigDecimal) extends FinanceMessage

case class MonthlyPayment(monthly: BigDecimal) extends FinanceMessage
case class TalkFinance(reply: String) extends FinanceMessage


case class TruliaListing(street: Option[String],
                          state: Option[String],
                          city: Option[String],
                          retrievedTimeStamp: Option[String],
                          link: Option[String],
                          price: Option[String],
                          beds: Option[String],
                          baths: Option[String],
                          sqFt: Option[String],
                          zipCode: Option[String]
                        ) extends FinanceMessage with CustomSerialize







