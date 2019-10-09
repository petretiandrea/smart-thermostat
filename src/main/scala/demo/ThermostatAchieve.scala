package demo

import agent.Agent.{Environment, EnvironmentExt}
import agent.ThermostatAgent
import akka.actor.{ActorSystem, Props}
import digitaltwin.ThermostatDT.{Cold, Heat, Off, State}
import digitaltwin._
import gui.{ThermostatGUI, VisualizerAgent}

class ThermostatAchieve(thermostat: ThermostatDT) extends ThermostatAgent(thermostat) {

  private val targetTemperature: Double = 20d
  private val THRESHOLD = 0.5
  private var achieved = false

  override def plan(env: Environment) {
    if(!achieved) {
      achieve(env, targetTemperature)
    }
  }

  private def achieve(env: Environment, targetTemperature: Double): Unit = {
    val temperature: Double = env.getValueOf("temperature")
    val state: State = env.getValueOf("state")

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


object ThermostatAchieve {
  def main(args: Array[String]): Unit = {
    val thermostat = ThermostatDT.wot("http://localhost:3000")
    val gui = new ThermostatGUI()
    val actorSystem = ActorSystem()
    val agentRef = actorSystem.actorOf(Props(new ThermostatAchieve(thermostat)))

    actorSystem.actorOf(Props(new VisualizerAgent(thermostat, agentRef, gui.regulatorPanel)))
  }
}
