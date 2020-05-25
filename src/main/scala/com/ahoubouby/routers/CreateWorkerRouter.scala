package com.ahoubouby.routers

import akka.actor.{Actor, ActorRef, Props}
import akka.cluster.routing.{ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing.BroadcastPool
import com.ahoubouby.actors.JobWorker

trait CreateWorkerRouter { this: Actor =>
  def createWorkerRouter: ActorRef = {
    context.actorOf(
      ClusterRouterPool(
        BroadcastPool(10),
        ClusterRouterPoolSettings(
          totalInstances = 100,
          maxInstancesPerNode = 20,
          allowLocalRoutees = false
        )
      ).props(Props[JobWorker]),
      name = "worker-router"
    )
  }
}
