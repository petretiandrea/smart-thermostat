package gui

import actor.ThermostatAgent.ChangeTargetTemperature
import actor.{Agent, ThermostatAgent}
import akka.actor.{ActorRef, ActorSystem, Props}
import dt.State

class VisualizerAgent(thermostatAgent: ActorRef, thermostatRegulator: ThermostatRegulatorPanel) extends ThermostatAgent {

  override def preStart(): Unit = {
    super.preStart()
    thermostatRegulator.addChangeTargetTemperatureListener { temp =>
      thermostatAgent ! ChangeTargetTemperature(temp)
    }
  }

  override def plan(sensed: Map[String, Any]): Unit = {
    thermostatRegulator.updateCurrentTemperature(sensed("temperature").asInstanceOf[Double])
    thermostatRegulator.updateCurrentStatus(sensed("state").asInstanceOf[State])
  }
}
