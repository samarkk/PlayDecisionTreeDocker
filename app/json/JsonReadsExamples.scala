package json

import play.api.libs.functional.syntax._
import play.api.libs.json._

object JsonReadsExamples {
  def main(args: Array[String]): Unit = {
    read1()
    println("Now time for read2")
    read2()
    read3()
  }
  case class Player(name: String, age: Int)
  case class Location(lat: Double, long: Double)
  case class Team(teamName: String, players: List[Player], location: Location)
  val jsonString =
    """{
      |  "teamName" : "Real Madrid FC",
      |  "players" : [ {
      |    "name" : "Ronaldo",
      |    "age" : 36
      |  }, {
      |    "name" : "Modric",
      |    "age" : 30
      |  }, {
      |    "name" : "Bale",
      |    "age" : 27
      |  } ],
      |  "location" : {
      |    "lat" : 40.4168,
      |    "long" : 3.7038
      |  }
      |}
      |""".stripMargin

  def read1() = {
    val jValue = Json.parse(jsonString)
    println((jValue \ "teamName").as[String])
    println((jValue \ "location" \ "lat").as[Double])
    println((jValue \ "players" \ 0 \ "name").as[String])

    val validate: JsResult[String] = (jValue \ "teamName").validate[String]
    validate match {
      case x: JsSuccess[String] => println(s"JsSuccess ${x.get}")
      case x: JsError           => println(x.errors)
    }

    val names: Seq[JsValue] = jValue \\ "name"
    println("The name field from the jvalue " + names.map(x => x.as[String]))
  }

  def read2(): Unit = {
    val jValue = Json.parse(jsonString)
    val temp = (JsPath \ "location" \ "lat").read[Double]
    println(jValue.as[Double](temp))

    implicit val playerReads: Reads[Player] = ((JsPath \ "name").read[String]
      and (JsPath \ "age").read[Int])(Player.apply _)

    implicit val locationReads: Reads[Location] = (
      (JsPath \ "lat").read[Double] and (JsPath \ "long").read[Double])(Location.apply _)

    implicit val teamReads: Reads[Team] = (
      (JsPath \ "teamName").read[String] and
      (JsPath \ "players").read[List[Player]] and
      (JsPath \ "location").read[Location])(Team.apply _)

    val teams = jValue.as[Team]
    println(s"teams in read2 is ${teams}")
  }

  def read3(): Unit = {
    val jValue: JsValue = Json.parse(jsonString)

    implicit val playerReads = Json.reads[Player]
    implicit val locationReads = Json.reads[Location]
    implicit val teamReads = Json.reads[Team]

    val teams = Json.fromJson[Team](jValue).get
    println(s"teams is $teams")
  }
}
