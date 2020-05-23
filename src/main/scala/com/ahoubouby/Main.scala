package com.ahoubouby

object Main extends App {

  import akka.actor.ActorSystem
  import com.ahoubouby.actors.ClusterDomainEventListener
  import com.typesafe.config.ConfigFactory

  val seedConfig = ConfigFactory.load("seed")
  val seedSystem = ActorSystem("words", seedConfig)
  val eventListenerActor = seedSystem.actorOf(
    ClusterDomainEventListener.props,
    ClusterDomainEventListener.name
  )

}
