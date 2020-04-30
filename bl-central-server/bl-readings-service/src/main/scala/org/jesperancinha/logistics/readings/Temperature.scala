package org.jesperancinha.logistics.readings

case class Temperature(deviceId: Int,
                       deviceSerialNumber: String,
                       deviceType: String,
                       unit: String,
                       timeOfReading: Long,
                       reading: Long)

object Temperature {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val temperatureReads: Reads[Temperature] = (
    (JsPath \ "deviceId").read[Int] and
      (JsPath \ "deviceSerialNumber").read[String] and
      (JsPath \ "deviceType").read[String] and
      (JsPath \ "unit").read[String] and
      (JsPath \ "timeOfReading").read[Long] and
      (JsPath \ "reading").read[Long]
    ) (Temperature.apply _)
}