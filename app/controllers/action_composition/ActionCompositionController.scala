package controllers.action_composition

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, ControllerComponents}

class ActionCompositionController @Inject()(loggingAction: LoggingAction,
                                            cc: ControllerComponents)
extends AbstractController(cc){

  def testLoggingAction() = loggingAction {
    Ok("Hello, world!")
  }

  def testAnotherLoggingAction(): Action[String] = loggingAction(parse.text) { request =>
    Ok("Got a body " + request.body.length + " bytes long")
  }

  def testOneMoreAction() = Logging {
    Action {
      Ok("Helloooo, world!")
    }
  }
}
