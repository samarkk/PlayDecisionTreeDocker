package modules

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{CovTypePredictionService, MLeapCTPService}

class GuiceModule(environment: Environment, configuration: Configuration) extends
  AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CovTypePredictionService]).to(classOf[MLeapCTPService])
  }
}
