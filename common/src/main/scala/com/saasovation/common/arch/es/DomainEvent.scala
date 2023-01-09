package com.saasovation.common.arch.es

import java.time.LocalDateTime

trait DomainEvent {

  val eventVersion: Int

  val occurredOn: LocalDateTime
}
