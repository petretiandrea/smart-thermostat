package gui

import agent.Agent.Environment
import agent.Agent.EnvironmentExt
import agent.ThermostatAgent
import agent.ThermostatAgent.ChangeTargetTemperature
import akka.actor.ActorRef
import digitaltwin.ThermostatDT


class VisualizerAgent(thermostat: ThermostatDT, thermostatAgent: ActorRef, thermostatRegulator: ThermostatRegulatorPanel) extends ThermostatAgent(thermostat) {

  override def preStart(): Unit = {
    super.preStart()
    thermostatRegulator.addChangeTargetTemperatureListener { temp =>
      thermostatAgent ! ChangeTargetTemperature(temp)
    }
  }

  override def plan(env: Environment): Unit = {
    thermostatRegulator.updateCurrentTemperature(env.getValueOf("temperature"))
    thermostatRegulator.updateCurrentStatus(env.getValueOf("state"))
  }
}
