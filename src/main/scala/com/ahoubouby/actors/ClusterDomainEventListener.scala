package com.ahoubouby.actors

import akka.actor.{ Actor, ActorLogging }

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.cluster.MemberStatus

class ClusterDomainEventListener extends Actor with ActorLogging {

  import context._

  Cluster(system = system).subscribe(self, classOf[ClusterDomainEvent])

  override def receive: Receive = {
    case MemberUp(member)     => log.info("member {} is UP", member)
    case MemberExited(member) => log.info("member {} is EXITED", member)
    case MemberRemoved(member, previousStatus) if previousStatus == MemberStatus.exiting ⇒
    case MemberRemoved(member, previousStatus) => log.error("member {} downed after unreachable, REMOVED", member)
    case UnreachableMember(m)                  => log.info("member {} Unreachable", m)
    case ReachableMember(m)                    => log.info("member {} Reachable", m)
    case s: CurrentClusterState                => log.info("current state {}", s)
  }

  override def postStop(): Unit = {
    Cluster(system).unsubscribe(self)
    super.postStop()
  }
}

object ClusterDomainEventListener {

  import akka.actor.Props

  def props = Props[ClusterDomainEventListener]
  def name  = "cluster-listener"
}
