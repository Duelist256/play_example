package utils

import java.net.URI

import com.typesafe.config.Config
import play.api.ConfigLoader

case class AppConfig(title: String, baseUri: URI)

object AppConfig {

  implicit val configLoader: ConfigLoader[AppConfig] = (rootConfig: Config, path: String) => {
    val config = rootConfig.getConfig(path)
    AppConfig(
      title = config.getString("title"),
      baseUri = new URI(config.getString("baseUri"))
    )
  }
}