package actor

import akka.actor.{ActorSystem, Props}
import dt.{Cold, Heat, Off, State}
import gui.{ThermostatGUI, VisualizerAgent}

class ThermostatAgent2 extends ThermostatAgent {

  private val targetTemperature: Double = 20d
  private val THRESHOLD = 0.5

  override def plan(sensed: Map[String, Any]): Unit = maintenance(sensed)

  private def maintenance(sensed: Map[String, Any]): Unit = {
    val temperature: Double = sensed("temperature").asInstanceOf[Double]
    val state: State = sensed("state").asInstanceOf[State]

    if(temperature - THRESHOLD > targetTemperature && `state` != Cold) {
      println("Cooling...")
      thermostat.startCooling()
    } else if(temperature + THRESHOLD < targetTemperature && state != Heat) {
      println("Heating...")
      thermostat.startHeating()
    } else if (Math.abs(temperature - targetTemperature) < THRESHOLD && state != Off) {
      println("Stop...")
      thermostat.stop()
    }
  }
}

object LaunchMaintenance {
  def main(args: Array[String]): Unit = {
    val gui = new ThermostatGUI()
    val actorSystem = ActorSystem()
    val agentRef = actorSystem.actorOf(Props[ThermostatAgent2])

    actorSystem.actorOf(Props(new VisualizerAgent(agentRef, gui.regulatorPanel)))
  }
}
