package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.swing._
import scala.swing.event._
    
object SchedulersSwing extends scala.swing.SimpleSwingApplication {

    def top = new MainFrame {
        title = "Swing Observables"

        val button = new Button {
            text = "Click"
        }

        contents = button

        val buttonClicks = Observable[Unit] { sub =>
            button.reactions += {
                case ButtonClicked(_) => sub.onNext(())
            }
        }

        buttonClicks.subscribe(_ => println("button clicked"))
    }

}

