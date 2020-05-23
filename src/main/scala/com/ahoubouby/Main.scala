package com.ahoubouby

object Main extends App {

  import akka.actor.ActorSystem
  import com.typesafe.config.ConfigFactory

  val seedConfig = ConfigFactory.load("seed")
  val seedSystem = ActorSystem("words", seedConfig)

}
