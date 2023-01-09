package com.saasovation.common.arch.es

trait DomainEventSubscriber[E <: DomainEvent] {

  def handleEvent(event: E): Unit

  // これなんだ...?
  def subscribedToEventType: Class[E]
}
