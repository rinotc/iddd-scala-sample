package com.saasovation.identityaccess.domain.user

import com.saasovation.common.arch.base.{Aggregate, Entity}
import com.saasovation.common.arch.es.DomainEvent

import java.time.LocalDateTime

final class User private (
    val id: UserId
) extends Entity
    with Aggregate {}

object User {

  sealed trait Event extends DomainEvent {
    override val occurredOn: LocalDateTime = LocalDateTime.now()
  }

  final case class UserPasswordChanged() extends Event {
    override val eventVersion: Int = ???
  }

  final case class UserRegistered() extends Event {
    override val eventVersion: Int = ???
  }

  final case class PersonNameChanged() extends Event {
    override val eventVersion: Int = ???
  }
}
