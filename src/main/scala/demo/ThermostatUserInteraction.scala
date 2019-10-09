package demo

import agent.Agent.{Environment, EnvironmentExt}
import agent.ThermostatAgent
import agent.ThermostatAgent.ChangeTargetTemperature
import akka.actor.{ActorSystem, Props}
import digitaltwin.ThermostatDT
import digitaltwin.ThermostatDT.{Cold, Heat, Off, State}
import gui.{ThermostatGUI, VisualizerAgent}

class ThermostatUserInteraction(thermostat: ThermostatDT) extends ThermostatAgent(thermostat) {

  private var targetTemperature: Double = 20d
  private val THRESHOLD = 0.2

  override def plan(env: Environment): Unit = maintenance(env)

  private def maintenance(env: Environment): Unit = {
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
    }
  }

  override def receive: Receive = super.receive orElse {
    case ChangeTargetTemperature(newTargetTemp) => targetTemperature = newTargetTemp
  }
}

object ThermostatUserInteraction {
  def main(args: Array[String]): Unit = {
    val thermostat = ThermostatDT.wot("http://localhost:3000")
    val gui = new ThermostatGUI()
    val actorSystem = ActorSystem()
    val agentRef = actorSystem.actorOf(Props(new ThermostatUserInteraction(thermostat)))

    actorSystem.actorOf(Props(new VisualizerAgent(thermostat, agentRef, gui.regulatorPanel)))
  }
}
