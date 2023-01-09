package com.saasovation.identityaccess.domain.user

import com.saasovation.common.arch.base.{IdentityUUID, IdentityUUIDCompanion}

import java.util.UUID

final case class UserId(value: UUID) extends IdentityUUID

object UserId extends IdentityUUIDCompanion[UserId]
