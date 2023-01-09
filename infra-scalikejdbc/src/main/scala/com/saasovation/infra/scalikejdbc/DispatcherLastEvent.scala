package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class DispatcherLastEvent(
  eventId: Long) {

  def save()(implicit session: DBSession = DispatcherLastEvent.autoSession): DispatcherLastEvent = DispatcherLastEvent.save(this)(session)

  def destroy()(implicit session: DBSession = DispatcherLastEvent.autoSession): Int = DispatcherLastEvent.destroy(this)(session)

}


object DispatcherLastEvent extends SQLSyntaxSupport[DispatcherLastEvent] {

  override val schemaName = Some("public")

  override val tableName = "dispatcher_last_event"

  override val columns = Seq("event_id")

  def apply(dle: SyntaxProvider[DispatcherLastEvent])(rs: WrappedResultSet): DispatcherLastEvent = apply(dle.resultName)(rs)
  def apply(dle: ResultName[DispatcherLastEvent])(rs: WrappedResultSet): DispatcherLastEvent = new DispatcherLastEvent(
    eventId = rs.get(dle.eventId)
  )

  val dle = DispatcherLastEvent.syntax("dle")

  override val autoSession = AutoSession

  def find(eventId: Long)(implicit session: DBSession = autoSession): Option[DispatcherLastEvent] = {
    withSQL {
      select.from(DispatcherLastEvent as dle).where.eq(dle.eventId, eventId)
    }.map(DispatcherLastEvent(dle.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatcherLastEvent] = {
    withSQL(select.from(DispatcherLastEvent as dle)).map(DispatcherLastEvent(dle.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatcherLastEvent as dle)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatcherLastEvent] = {
    withSQL {
      select.from(DispatcherLastEvent as dle).where.append(where)
    }.map(DispatcherLastEvent(dle.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatcherLastEvent] = {
    withSQL {
      select.from(DispatcherLastEvent as dle).where.append(where)
    }.map(DispatcherLastEvent(dle.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatcherLastEvent as dle).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    eventId: Long)(implicit session: DBSession = autoSession): DispatcherLastEvent = {
    withSQL {
      insert.into(DispatcherLastEvent).namedValues(
        column.eventId -> eventId
      )
    }.update.apply()

    DispatcherLastEvent(
      eventId = eventId)
  }

  def batchInsert(entities: collection.Seq[DispatcherLastEvent])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("eventId") -> entity.eventId))
    SQL("""insert into dispatcher_last_event(
      event_id
    ) values (
      {eventId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: DispatcherLastEvent)(implicit session: DBSession = autoSession): DispatcherLastEvent = {
    withSQL {
      update(DispatcherLastEvent).set(
        column.eventId -> entity.eventId
      ).where.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatcherLastEvent)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatcherLastEvent).where.eq(column.eventId, entity.eventId) }.update.apply()
  }

}
