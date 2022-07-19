package actors

import akka.actor.typed.scaladsl.Behaviors
import models._

object Accountant {


  def apply: Behaviors.Receive[FinanceMessage] = Behaviors.receiveMessage[FinanceMessage]{
    case DownPayment(sender, down) =>
      sender ! TalkFinance(s"Accountant received $down")
      Behaviors.same
  }


}
