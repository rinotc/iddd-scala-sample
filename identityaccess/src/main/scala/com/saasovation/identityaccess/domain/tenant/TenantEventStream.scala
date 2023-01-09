package com.saasovation.identityaccess.domain.tenant

import com.saasovation.common.event.sourcing.EventStream

class TenantEventStream(val events: LazyList[Tenant.Event]) extends EventStream[Tenant.Event] {
  override val streamName: String = "TenantEventStream"
  override val version: Int       = 1
}
