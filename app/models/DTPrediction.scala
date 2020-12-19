package models

import play.api.libs.json.{Json, OFormat}

case class DTPrediction(dp: String, prediction: Double)

object DTPrediction{
  implicit val DTPredictionFormat: OFormat[DTPrediction] = Json.format[DTPrediction]
}
