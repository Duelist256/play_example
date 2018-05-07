package controllers.filters

import javax.inject.Inject
import play.api.http.EnabledFilters
import play.http.DefaultHttpFilters

class Filters @Inject()(defaultFilters: EnabledFilters, loggingFilter: LoggingFilter)
  extends DefaultHttpFilters(defaultFilters.filters :+ loggingFilter : _*)
