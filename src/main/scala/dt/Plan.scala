package dt

trait Plan {
  def needToSchedule(sensed: Map[String, Any]): Boolean
  def removeFromSchedule()
  def execute(sensed: Map[String, Any])
}

object Plan {

  class PlanAchieve extends Plan {

    override def needToSchedule(sensed: Map[String, Any]): Boolean = ???
    override def removeFromSchedule(): Unit = ???
    override def execute(sensed: Map[String, Any]): Unit = ???
  }

  class PlanForever() extends Plan {
    private var schedule = true
    override def needToSchedule(sensed: Map[String, Any]): Boolean = schedule
    override def removeFromSchedule(): Unit = schedule = false

    override def execute(sensed: Map[String, Any]): Unit = ???
  }
}
