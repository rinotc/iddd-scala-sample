package com.saasovation.identityaccess.domain.tenant

import com.saasovation.common.arch.base.{IdentityUUID, IdentityUUIDCompanion}

import java.util.UUID

final case class TenantId(value: UUID) extends IdentityUUID

object TenantId extends IdentityUUIDCompanion[TenantId]
