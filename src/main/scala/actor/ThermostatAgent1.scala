package actor

import akka.actor.{ActorSystem, Props}
import dt._
import gui.{ThermostatGUI, VisualizerAgent}

class ThermostatAgent1 extends ThermostatAgent {

  private val targetTemperature: Double = 20d
  private val THRESHOLD = 0.5
  private var achieved = false

  override def plan(sensed: Map[String, Any]) {
    if(!achieved) {
      achieve(sensed, targetTemperature)
    }
  }

  private def achieve(sensed: Map[String, Any], targetTemperature: Double): Unit = {
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
      achieved = true
    }
  }
}


object LaunchAchieve {
  def main(args: Array[String]): Unit = {
    val gui = new ThermostatGUI()
    val actorSystem = ActorSystem()
    val agentRef = actorSystem.actorOf(Props[ThermostatAgent1])

    actorSystem.actorOf(Props(new VisualizerAgent(agentRef, gui.regulatorPanel)))
  }
}
