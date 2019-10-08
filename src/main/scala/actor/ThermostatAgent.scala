package actor

import dt._

object ThermostatAgent {
  case class ChangeTargetTemperature(targetTemp: Double)
}

abstract class ThermostatAgent() extends Agent {

  protected val thermostat : ThermostatDT = ThermostatDT.wot("http://localhost:3000")

  override def sense(): Map[String, Any] = {
    var map = Map[String, Any]()
    map += "temperature" -> thermostat.temperature()
    map += "state" -> thermostat.state()
    map
  }
}
