package com.saasovation.identityaccess.domain.tenant

import com.saasovation.common.arch.base.Entity
import com.saasovation.common.arch.es.DomainEvent

import java.time.LocalDateTime

final class Tenant private (
    val id: TenantId,
    val active: Boolean,
    val description: String,
    val name: String
) extends Entity {

  import Tenant._

  def activate(): Either[String, TenantActivated] =
    Either.cond(isNotActive, TenantActivated(id), "tenant is already activated.")

  private def isNotActive: Boolean = !active
}

object Tenant {

  sealed trait Event extends DomainEvent

  final case class TenantActivated(tenantId: TenantId) extends Event {
    override val eventVersion: Int         = 1
    override val occurredOn: LocalDateTime = LocalDateTime.now()
  }


}
