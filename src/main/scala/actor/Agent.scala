package actor

import akka.actor.{Actor, ActorLogging, Scheduler}

import scala.collection.immutable.Map
import scala.concurrent.duration._

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

  def sense(): Map[String, Any]
  def plan(sensed: Map[String, Any])
}