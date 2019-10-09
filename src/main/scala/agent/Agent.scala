package agent

import agent.Agent.Environment
import akka.actor.{Actor, ActorLogging, Scheduler}

import scala.collection.immutable.Map
import scala.concurrent.duration._

object Agent {
  type Environment = Map[String, Any]
  implicit class EnvironmentExt(env: Environment) {
    def getValueOf[T](key: String) : T = env(key).asInstanceOf[T]
  }
}

abstract class Agent extends Actor with ActorLogging {

  object LifecycleTick

  private val scheduler: Scheduler = context.system.scheduler
  private val lifecyclePeriod = 1 second

  override def preStart(): Unit = {
    self ! LifecycleTick
  }

  override def receive: Receive = {
    case LifecycleTick => {
      plan(sense())
      scheduler.scheduleOnce(lifecyclePeriod, self, LifecycleTick)(context.dispatcher)
    }
  }

  def sense(): Environment
  def plan(sensed: Environment)
}