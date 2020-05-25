package com.ahoubouby

object Main extends App {

  import akka.actor.ActorSystem
  import com.ahoubouby.actors.ClusterDomainEventListener
  import akka.cluster.Cluster
  import com.typesafe.config.ConfigFactory

  val config = ConfigFactory.load()
  val system = ActorSystem("words", config)

  println(s"Starting node with roles: ${Cluster(system).selfRoles}")

  if (system.settings.config
        .getStringList("akka.cluster.roles")
        .contains("master")) {
    Cluster(system).registerOnMemberUp {
      import com.ahoubouby.actors.JobReceptionist
      import com.ahoubouby.actors.JobReceptionist.JobRequest
      val receptionist =
        system.actorOf(JobReceptionist.props, JobReceptionist.name)
      println("Master node is ready.")

      val text = List(
        "this is a test",
        "of some very naive word counting",
        "but what can you say",
        "it is what it is"
      )
      receptionist ! JobRequest(
        "the first job",
        (1 to 100000).flatMap(i => text ++ text).toList
      )
      system.actorOf(
        ClusterDomainEventListener.props,
        ClusterDomainEventListener.name
      )
    }
  }

}
