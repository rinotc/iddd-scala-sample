package com.saasovation.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class SortedEvent(
  eventId: Long,
  eventBody: Any,
  occurredOn: LocalDateTime,
  typeName: String) {

  def save()(implicit session: DBSession = SortedEvent.autoSession): SortedEvent = SortedEvent.save(this)(session)

  def destroy()(implicit session: DBSession = SortedEvent.autoSession): Int = SortedEvent.destroy(this)(session)

}


object SortedEvent extends SQLSyntaxSupport[SortedEvent] {

  override val schemaName = Some("public")

  override val tableName = "sorted_event"

  override val columns = Seq("event_id", "event_body", "occurred_on", "type_name")

  def apply(se: SyntaxProvider[SortedEvent])(rs: WrappedResultSet): SortedEvent = apply(se.resultName)(rs)
  def apply(se: ResultName[SortedEvent])(rs: WrappedResultSet): SortedEvent = new SortedEvent(
    eventId = rs.get(se.eventId),
    eventBody = rs.any(se.eventBody),
    occurredOn = rs.get(se.occurredOn),
    typeName = rs.get(se.typeName)
  )

  val se = SortedEvent.syntax("se")

  override val autoSession = AutoSession

  def find(eventId: Long)(implicit session: DBSession = autoSession): Option[SortedEvent] = {
    withSQL {
      select.from(SortedEvent as se).where.eq(se.eventId, eventId)
    }.map(SortedEvent(se.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SortedEvent] = {
    withSQL(select.from(SortedEvent as se)).map(SortedEvent(se.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SortedEvent as se)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SortedEvent] = {
    withSQL {
      select.from(SortedEvent as se).where.append(where)
    }.map(SortedEvent(se.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SortedEvent] = {
    withSQL {
      select.from(SortedEvent as se).where.append(where)
    }.map(SortedEvent(se.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SortedEvent as se).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    eventBody: Any,
    occurredOn: LocalDateTime,
    typeName: String)(implicit session: DBSession = autoSession): SortedEvent = {
    val generatedKey = withSQL {
      insert.into(SortedEvent).namedValues(
        (column.eventBody, ParameterBinder(eventBody, (ps, i) => ps.setObject(i, eventBody))),
        column.occurredOn -> occurredOn,
        column.typeName -> typeName
      )
    }.updateAndReturnGeneratedKey.apply()

    SortedEvent(
      eventId = generatedKey,
      eventBody = eventBody,
      occurredOn = occurredOn,
      typeName = typeName)
  }

  def batchInsert(entities: collection.Seq[SortedEvent])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("eventBody") -> entity.eventBody,
        Symbol("occurredOn") -> entity.occurredOn,
        Symbol("typeName") -> entity.typeName))
    SQL("""insert into sorted_event(
      event_body,
      occurred_on,
      type_name
    ) values (
      {eventBody},
      {occurredOn},
      {typeName}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: SortedEvent)(implicit session: DBSession = autoSession): SortedEvent = {
    withSQL {
      update(SortedEvent).set(
        column.eventId -> entity.eventId,
        (column.eventBody, ParameterBinder(entity.eventBody, (ps, i) => ps.setObject(i, entity.eventBody))),
        column.occurredOn -> entity.occurredOn,
        column.typeName -> entity.typeName
      ).where.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: SortedEvent)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SortedEvent).where.eq(column.eventId, entity.eventId) }.update.apply()
  }

}
