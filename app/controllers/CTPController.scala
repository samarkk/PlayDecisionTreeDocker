package controllers

import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import services.CovTypePredictionService


class CTPController @Inject()(ctps: CovTypePredictionService, cc: ControllerComponents)
  extends AbstractController(cc) {

  // curl localhost:9000/api/cvtpred/2596,51,3,258,0,510,221,232,148,6279,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0

  def cvtpredict(dp: String): Action[AnyContent] = Action {
    Ok(Json.toJson(ctps.makePredictionForOneRecord(dp)))
  }

  // curl -X GET -H "Content-Type: application/json" -d '{"items": ["2595,45,2,153,-1,391,220,234,150,6172,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0","2606,45,7,270,5,633,222,225,138,6256,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0"]}' localhost:9000/api/cvtpredm

  def cvtpItems: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val body = request.body
    val bodyJson: Option[JsValue] = body.asJson
    val itemsToPredict = bodyJson.map(json => (json \ "items").as[Array[String]]).getOrElse(Array(""))
    val ans = ctps.makePredictionsForMultipleRecords(itemsToPredict)
    Ok(Json.toJson(ans))
  }

  // in the browser
  //http://localhost:9000/cvtpredlist?item=2596,51,3,258,0,510,221,232,148,6279,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0&item=2579,132,6,300,-15,67,230,237,140,6031,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0

  def cvtpListItems(itemsList: List[String]): Action[AnyContent] = Action { implicit request =>
    val items = itemsList.toArray
    val ans = ctps.makePredictionsForMultipleRecords(items)
    Ok(Json.toJson(ans))
  }


}
