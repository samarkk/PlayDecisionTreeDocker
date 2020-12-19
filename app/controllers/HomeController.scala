package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.Configuration
import play.api.mvc._
import play.twirl.api.Html

import scala.util.{Failure, Success, Try}

class HomeController @Inject()(
                                cc: ControllerComponents, config: Configuration) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("Hello World")
      .withHeaders("Server" -> "Play")
      .withCookies(Cookie("id", scala.util.Random.nextInt().toString))
  }

  def checkConfig(): Action[AnyContent] = Action { implicit request =>
    Ok(config.get[String]("floc"))
  }

  def hello(name: String): Action[AnyContent] = Action {
    val c: Html = views.html.index(name)
    Ok(c)
  }

  def getUser(name: String, age: Int): Action[AnyContent] = helloDetail(name, age)

  def helloDetail(name: String, age: Int): Action[AnyContent] = Action {
    println("helloDetail was called")
    Ok(s"Hello $name of $age")
  }

  // curl -X POST   "name=mahesh&age=50" localhost:9000/user/addUser
  def addUser(): Action[AnyContent] = Action { implicit request =>
    println("addUser is hit")
    val body = request.body
    body.asFormUrlEncoded match {
      case Some(map) =>
        Ok(s"The user of name ${map("name").head} and age ${map("age").head} has been created\n")
      case None => BadRequest("Unknown body format")
    }
  }

  def sqrt(num: String): Action[AnyContent] = Action { implicit request =>
    Try(num.toInt) match {
      case Success(ans) if ans >= 0 => Ok(s"The answer is ${math.sqrt(ans)}\n")
      case Success(ans) => BadRequest(s"The input ($num) must be greater than zero\n")
      case Failure(ex) => InternalServerError(s"could not extract the contents from $num\n")
    }
  }
}
