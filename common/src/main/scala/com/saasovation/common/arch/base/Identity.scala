package com.saasovation.common.arch.base

import java.util.UUID

trait Identity[U] {
  def value: U
}

trait IdentityUUID extends Identity[UUID]

trait IdentityUUIDCompanion[ID <: IdentityUUID] {

  def apply(value: UUID): ID

  def gen(): ID = apply(UUID.randomUUID())
}
