package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class EventStore(
  eventId: Long,
  eventBody: Any,
  eventType: String,
  streamName: String,
  streamVersion: Int) {

  def save()(implicit session: DBSession = EventStore.autoSession): EventStore = EventStore.save(this)(session)

  def destroy()(implicit session: DBSession = EventStore.autoSession): Int = EventStore.destroy(this)(session)

}


object EventStore extends SQLSyntaxSupport[EventStore] {

  override val schemaName = Some("public")

  override val tableName = "event_store"

  override val columns = Seq("event_id", "event_body", "event_type", "stream_name", "stream_version")

  def apply(es: SyntaxProvider[EventStore])(rs: WrappedResultSet): EventStore = apply(es.resultName)(rs)
  def apply(es: ResultName[EventStore])(rs: WrappedResultSet): EventStore = new EventStore(
    eventId = rs.get(es.eventId),
    eventBody = rs.any(es.eventBody),
    eventType = rs.get(es.eventType),
    streamName = rs.get(es.streamName),
    streamVersion = rs.get(es.streamVersion)
  )

  val es = EventStore.syntax("es")

  override val autoSession = AutoSession

  def find(eventId: Long)(implicit session: DBSession = autoSession): Option[EventStore] = {
    withSQL {
      select.from(EventStore as es).where.eq(es.eventId, eventId)
    }.map(EventStore(es.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EventStore] = {
    withSQL(select.from(EventStore as es)).map(EventStore(es.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EventStore as es)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EventStore] = {
    withSQL {
      select.from(EventStore as es).where.append(where)
    }.map(EventStore(es.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EventStore] = {
    withSQL {
      select.from(EventStore as es).where.append(where)
    }.map(EventStore(es.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EventStore as es).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    eventBody: Any,
    eventType: String,
    streamName: String,
    streamVersion: Int)(implicit session: DBSession = autoSession): EventStore = {
    val generatedKey = withSQL {
      insert.into(EventStore).namedValues(
        (column.eventBody, ParameterBinder(eventBody, (ps, i) => ps.setObject(i, eventBody))),
        column.eventType -> eventType,
        column.streamName -> streamName,
        column.streamVersion -> streamVersion
      )
    }.updateAndReturnGeneratedKey.apply()

    EventStore(
      eventId = generatedKey,
      eventBody = eventBody,
      eventType = eventType,
      streamName = streamName,
      streamVersion = streamVersion)
  }

  def batchInsert(entities: collection.Seq[EventStore])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("eventBody") -> entity.eventBody,
        Symbol("eventType") -> entity.eventType,
        Symbol("streamName") -> entity.streamName,
        Symbol("streamVersion") -> entity.streamVersion))
    SQL("""insert into event_store(
      event_body,
      event_type,
      stream_name,
      stream_version
    ) values (
      {eventBody},
      {eventType},
      {streamName},
      {streamVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: EventStore)(implicit session: DBSession = autoSession): EventStore = {
    withSQL {
      update(EventStore).set(
        column.eventId -> entity.eventId,
        (column.eventBody, ParameterBinder(entity.eventBody, (ps, i) => ps.setObject(i, entity.eventBody))),
        column.eventType -> entity.eventType,
        column.streamName -> entity.streamName,
        column.streamVersion -> entity.streamVersion
      ).where.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: EventStore)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EventStore).where.eq(column.eventId, entity.eventId) }.update.apply()
  }

}
