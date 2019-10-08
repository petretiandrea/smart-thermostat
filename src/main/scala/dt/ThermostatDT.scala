package dt

import play.api.libs.json.{JsValue, Json}
import scalaj.http.{Http, HttpRequest}

trait ThermostatDT {
  def startHeating()
  def startCooling()
  def stop()

  def temperature(): Double
  def state(): State
}

trait State
case object Heat extends State
case object Cold extends State
case object Off extends State

object ThermostatDT {

  def wot(baseUrl: String) = new ThermostatDTWot(baseUrl)

  class ThermostatDTWot(private val baseUrl: String) extends ThermostatDT {

    private val ENDPOINT_TEMPERATURE = "/thermostat/properties/temperature"
    private val ENDPOINT_STATE = "/thermostat/properties/state"
    private val ENDPOINT_HEAT = "/thermostat/actions/heat"
    private val ENDPOINT_COLD = "/thermostat/actions/cold"
    private val ENDPOINT_OFF = "/thermostat/actions/off"

    override def startHeating(): Unit = makePost(ENDPOINT_HEAT)
    override def startCooling(): Unit = makePost(ENDPOINT_COLD)
    override def stop(): Unit = makePost(ENDPOINT_OFF)
    override def temperature(): Double = (makeGet(ENDPOINT_TEMPERATURE) \ "temperature").as[Double]
    override def state(): State = (makeGet(ENDPOINT_STATE) \ "state").as[String] match {
      case "heat" => Heat
      case "cold" => Cold
      case _ => Off
    }

    private def makeGet(endpoint: String): JsValue = {
      processResponse(Http(buildUri(endpoint)))
    }

    private def makePost(endpoint: String): Unit = {
      processResponse(Http(buildUri(endpoint)).postData(""))
    }

    private def processResponse(httpRequest: HttpRequest): JsValue = {
      val httpResponse = httpRequest.asString
      if(httpResponse.isSuccess) {
        Json.parse(httpResponse.body)
      } else {
        Json.parse("{}")
      }
    }

    private def buildUri(endpoint: String): String = s"${baseUrl}${endpoint}"
  }

}
