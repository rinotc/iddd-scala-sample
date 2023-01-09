package com.saasovation.common.arch.es

import scala.collection.mutable
object DomainEventPublisher {

  private val subscribers: mutable.Seq[DomainEventSubscriber[_]] = mutable.Seq.empty
  private var isPublishing: Boolean                              = false

  private def isNotPublishing: Boolean = !isPublishing

  private def makePublishing(): Unit = {
    isPublishing = true
  }

  private def donePublishing(): Unit = {
    isPublishing = false
  }

  def publish[E <: DomainEvent](event: E): Unit = {
    if (isNotPublishing && subscribers.nonEmpty) {
      makePublishing()


    }
  }
}
