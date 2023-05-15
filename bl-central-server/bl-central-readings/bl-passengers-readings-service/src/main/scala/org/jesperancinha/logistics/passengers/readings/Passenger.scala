package org.jesperancinha.logistics.passengers.readings

case class Passenger(id: Long,
                     firstName: String,
                     lastName: String,
                     gender: String,
                     carriageId: Long,
                     weight: Long,
                     unit: String,
                     status: String,
                     lat: Float,
                     lon: Float,
                     timeOfReading: Long)

object Passenger {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val humidityReads: Reads[Passenger] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "firstName").read[String] and
      (JsPath \ "lastName").read[String] and
      (JsPath \ "gender").read[String] and
      (JsPath \ "carriageId").read[Long] and
      (JsPath \ "weight").read[Long] and
      (JsPath \ "unit").read[String] and
      (JsPath \ "status").read[String] and
      (JsPath \ "lat").read[Float] and
      (JsPath \ "lon").read[Float] and
      (JsPath \ "timeOfReading").read[Long]

    ) (Passenger.apply _)
}