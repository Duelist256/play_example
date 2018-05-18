package controllers.using_the_cache

import javax.inject._
import play.api.mvc._
import play.api.cache.Cached


@Singleton
class CachedExample @Inject()(cached: Cached, cc: ControllerComponents) extends AbstractController(cc) {

  def index: EssentialAction = cached("homepage") {
    Action {
      Ok("Hello world")
    }
  }

}