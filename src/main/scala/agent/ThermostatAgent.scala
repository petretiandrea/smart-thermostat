package agent

import agent.Agent.Environment
import digitaltwin._

object ThermostatAgent {
  case class ChangeTargetTemperature(targetTemp: Double)
}

abstract class ThermostatAgent(thermostat : ThermostatDT) extends Agent {

  override def sense(): Environment = {
    var env = Map[String, Any]()
    env += "temperature" -> thermostat.temperature()
    env += "state" -> thermostat.state()
    env
  }
}
