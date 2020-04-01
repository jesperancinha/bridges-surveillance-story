package org.jesperancinha.bridgelogistics.readings

import play.api.libs.json.Json

case class Temperature(deviceId: Int,
                       deviceSerialNumber: String,
                       deviceType: String,
                       unit: String,
                       periodicity: String,
                       reading: String)

object Temperature {
  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val temperatureReads: Reads[Temperature] = (
      (JsPath \ "deviceId").read[Int] and
      (JsPath \ "deviceSerialNumber").read[String] and
      (JsPath \ "deviceType").read[String] and
      (JsPath \ "unit").read[String] and
      (JsPath \ "periodicity").read[String] and
      (JsPath \ "reading").read[String]
    ) (Temperature.apply _)
}