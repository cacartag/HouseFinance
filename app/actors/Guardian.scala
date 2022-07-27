package actors

import akka.actor.typed.scaladsl.Behaviors
import models.{DownPayment, FinanceMessage}

object Guardian {

  def apply = Behaviors.setup[FinanceMessage]{
    context =>

      val accountant = context.spawn(Accountant.apply, "Accountant")


      Behaviors.receiveMessage[FinanceMessage]{
        case msg: DownPayment =>
          accountant ! msg
          Behaviors.same
      }

  }

}




