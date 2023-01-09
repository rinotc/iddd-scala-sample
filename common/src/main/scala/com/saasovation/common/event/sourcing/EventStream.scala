package com.saasovation.common.event.sourcing

import com.saasovation.common.arch.es.DomainEvent

trait EventStream[E <: DomainEvent] {

  val streamName: String

  val version: Int

  val events: LazyList[E]
}
