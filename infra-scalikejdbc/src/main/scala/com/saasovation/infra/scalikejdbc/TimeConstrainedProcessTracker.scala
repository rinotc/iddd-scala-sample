package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class TimeConstrainedProcessTracker(
  timeConstrainedProcessTrackerId: Long,
  allowedDuration: Long,
  completed: Boolean,
  description: String,
  processId: String,
  processInformedOfTimeout: Boolean,
  processTimedOutEventType: String,
  retryCount: Int,
  tenantId: String,
  timeoutOccursOn: Long,
  totalRetriesPermitted: Long,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = TimeConstrainedProcessTracker.autoSession): TimeConstrainedProcessTracker = TimeConstrainedProcessTracker.save(this)(session)

  def destroy()(implicit session: DBSession = TimeConstrainedProcessTracker.autoSession): Int = TimeConstrainedProcessTracker.destroy(this)(session)

}


object TimeConstrainedProcessTracker extends SQLSyntaxSupport[TimeConstrainedProcessTracker] {

  override val schemaName = Some("public")

  override val tableName = "time_constrained_process_tracker"

  override val columns = Seq("time_constrained_process_tracker_id", "allowed_duration", "completed", "description", "process_id", "process_informed_of_timeout", "process_timed_out_event_type", "retry_count", "tenant_id", "timeout_occurs_on", "total_retries_permitted", "concurrency_version")

  def apply(tcpt: SyntaxProvider[TimeConstrainedProcessTracker])(rs: WrappedResultSet): TimeConstrainedProcessTracker = apply(tcpt.resultName)(rs)
  def apply(tcpt: ResultName[TimeConstrainedProcessTracker])(rs: WrappedResultSet): TimeConstrainedProcessTracker = new TimeConstrainedProcessTracker(
    timeConstrainedProcessTrackerId = rs.get(tcpt.timeConstrainedProcessTrackerId),
    allowedDuration = rs.get(tcpt.allowedDuration),
    completed = rs.get(tcpt.completed),
    description = rs.get(tcpt.description),
    processId = rs.get(tcpt.processId),
    processInformedOfTimeout = rs.get(tcpt.processInformedOfTimeout),
    processTimedOutEventType = rs.get(tcpt.processTimedOutEventType),
    retryCount = rs.get(tcpt.retryCount),
    tenantId = rs.get(tcpt.tenantId),
    timeoutOccursOn = rs.get(tcpt.timeoutOccursOn),
    totalRetriesPermitted = rs.get(tcpt.totalRetriesPermitted),
    concurrencyVersion = rs.get(tcpt.concurrencyVersion)
  )

  val tcpt = TimeConstrainedProcessTracker.syntax("tcpt")

  override val autoSession = AutoSession

  def find(timeConstrainedProcessTrackerId: Long)(implicit session: DBSession = autoSession): Option[TimeConstrainedProcessTracker] = {
    withSQL {
      select.from(TimeConstrainedProcessTracker as tcpt).where.eq(tcpt.timeConstrainedProcessTrackerId, timeConstrainedProcessTrackerId)
    }.map(TimeConstrainedProcessTracker(tcpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TimeConstrainedProcessTracker] = {
    withSQL(select.from(TimeConstrainedProcessTracker as tcpt)).map(TimeConstrainedProcessTracker(tcpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TimeConstrainedProcessTracker as tcpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TimeConstrainedProcessTracker] = {
    withSQL {
      select.from(TimeConstrainedProcessTracker as tcpt).where.append(where)
    }.map(TimeConstrainedProcessTracker(tcpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TimeConstrainedProcessTracker] = {
    withSQL {
      select.from(TimeConstrainedProcessTracker as tcpt).where.append(where)
    }.map(TimeConstrainedProcessTracker(tcpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TimeConstrainedProcessTracker as tcpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    allowedDuration: Long,
    completed: Boolean,
    description: String,
    processId: String,
    processInformedOfTimeout: Boolean,
    processTimedOutEventType: String,
    retryCount: Int,
    tenantId: String,
    timeoutOccursOn: Long,
    totalRetriesPermitted: Long,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): TimeConstrainedProcessTracker = {
    val generatedKey = withSQL {
      insert.into(TimeConstrainedProcessTracker).namedValues(
        column.allowedDuration -> allowedDuration,
        column.completed -> completed,
        column.description -> description,
        column.processId -> processId,
        column.processInformedOfTimeout -> processInformedOfTimeout,
        column.processTimedOutEventType -> processTimedOutEventType,
        column.retryCount -> retryCount,
        column.tenantId -> tenantId,
        column.timeoutOccursOn -> timeoutOccursOn,
        column.totalRetriesPermitted -> totalRetriesPermitted,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.updateAndReturnGeneratedKey.apply()

    TimeConstrainedProcessTracker(
      timeConstrainedProcessTrackerId = generatedKey,
      allowedDuration = allowedDuration,
      completed = completed,
      description = description,
      processId = processId,
      processInformedOfTimeout = processInformedOfTimeout,
      processTimedOutEventType = processTimedOutEventType,
      retryCount = retryCount,
      tenantId = tenantId,
      timeoutOccursOn = timeoutOccursOn,
      totalRetriesPermitted = totalRetriesPermitted,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[TimeConstrainedProcessTracker])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("allowedDuration") -> entity.allowedDuration,
        Symbol("completed") -> entity.completed,
        Symbol("description") -> entity.description,
        Symbol("processId") -> entity.processId,
        Symbol("processInformedOfTimeout") -> entity.processInformedOfTimeout,
        Symbol("processTimedOutEventType") -> entity.processTimedOutEventType,
        Symbol("retryCount") -> entity.retryCount,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("timeoutOccursOn") -> entity.timeoutOccursOn,
        Symbol("totalRetriesPermitted") -> entity.totalRetriesPermitted,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into time_constrained_process_tracker(
      allowed_duration,
      completed,
      description,
      process_id,
      process_informed_of_timeout,
      process_timed_out_event_type,
      retry_count,
      tenant_id,
      timeout_occurs_on,
      total_retries_permitted,
      concurrency_version
    ) values (
      {allowedDuration},
      {completed},
      {description},
      {processId},
      {processInformedOfTimeout},
      {processTimedOutEventType},
      {retryCount},
      {tenantId},
      {timeoutOccursOn},
      {totalRetriesPermitted},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: TimeConstrainedProcessTracker)(implicit session: DBSession = autoSession): TimeConstrainedProcessTracker = {
    withSQL {
      update(TimeConstrainedProcessTracker).set(
        column.timeConstrainedProcessTrackerId -> entity.timeConstrainedProcessTrackerId,
        column.allowedDuration -> entity.allowedDuration,
        column.completed -> entity.completed,
        column.description -> entity.description,
        column.processId -> entity.processId,
        column.processInformedOfTimeout -> entity.processInformedOfTimeout,
        column.processTimedOutEventType -> entity.processTimedOutEventType,
        column.retryCount -> entity.retryCount,
        column.tenantId -> entity.tenantId,
        column.timeoutOccursOn -> entity.timeoutOccursOn,
        column.totalRetriesPermitted -> entity.totalRetriesPermitted,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.timeConstrainedProcessTrackerId, entity.timeConstrainedProcessTrackerId)
    }.update.apply()
    entity
  }

  def destroy(entity: TimeConstrainedProcessTracker)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TimeConstrainedProcessTracker).where.eq(column.timeConstrainedProcessTrackerId, entity.timeConstrainedProcessTrackerId) }.update.apply()
  }

}
