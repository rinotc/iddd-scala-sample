package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class CalendarEntrySharers(
  calendarEntrySharersId: Int,
  calendarId: String,
  participantEmailAddress: String,
  participantIdentity: String,
  participantName: String,
  tenantId: String) {

  def save()(implicit session: DBSession = CalendarEntrySharers.autoSession): CalendarEntrySharers = CalendarEntrySharers.save(this)(session)

  def destroy()(implicit session: DBSession = CalendarEntrySharers.autoSession): Int = CalendarEntrySharers.destroy(this)(session)

}


object CalendarEntrySharers extends SQLSyntaxSupport[CalendarEntrySharers] {

  override val schemaName = Some("public")

  override val tableName = "calendar_entry_sharers"

  override val columns = Seq("calendar_entry_sharers_id", "calendar_id", "participant_email_address", "participant_identity", "participant_name", "tenant_id")

  def apply(ces: SyntaxProvider[CalendarEntrySharers])(rs: WrappedResultSet): CalendarEntrySharers = apply(ces.resultName)(rs)
  def apply(ces: ResultName[CalendarEntrySharers])(rs: WrappedResultSet): CalendarEntrySharers = new CalendarEntrySharers(
    calendarEntrySharersId = rs.get(ces.calendarEntrySharersId),
    calendarId = rs.get(ces.calendarId),
    participantEmailAddress = rs.get(ces.participantEmailAddress),
    participantIdentity = rs.get(ces.participantIdentity),
    participantName = rs.get(ces.participantName),
    tenantId = rs.get(ces.tenantId)
  )

  val ces = CalendarEntrySharers.syntax("ces")

  override val autoSession = AutoSession

  def find(calendarEntrySharersId: Int)(implicit session: DBSession = autoSession): Option[CalendarEntrySharers] = {
    withSQL {
      select.from(CalendarEntrySharers as ces).where.eq(ces.calendarEntrySharersId, calendarEntrySharersId)
    }.map(CalendarEntrySharers(ces.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CalendarEntrySharers] = {
    withSQL(select.from(CalendarEntrySharers as ces)).map(CalendarEntrySharers(ces.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CalendarEntrySharers as ces)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CalendarEntrySharers] = {
    withSQL {
      select.from(CalendarEntrySharers as ces).where.append(where)
    }.map(CalendarEntrySharers(ces.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CalendarEntrySharers] = {
    withSQL {
      select.from(CalendarEntrySharers as ces).where.append(where)
    }.map(CalendarEntrySharers(ces.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CalendarEntrySharers as ces).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    calendarId: String,
    participantEmailAddress: String,
    participantIdentity: String,
    participantName: String,
    tenantId: String)(implicit session: DBSession = autoSession): CalendarEntrySharers = {
    val generatedKey = withSQL {
      insert.into(CalendarEntrySharers).namedValues(
        column.calendarId -> calendarId,
        column.participantEmailAddress -> participantEmailAddress,
        column.participantIdentity -> participantIdentity,
        column.participantName -> participantName,
        column.tenantId -> tenantId
      )
    }.updateAndReturnGeneratedKey.apply()

    CalendarEntrySharers(
      calendarEntrySharersId = generatedKey.toInt,
      calendarId = calendarId,
      participantEmailAddress = participantEmailAddress,
      participantIdentity = participantIdentity,
      participantName = participantName,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[CalendarEntrySharers])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("calendarId") -> entity.calendarId,
        Symbol("participantEmailAddress") -> entity.participantEmailAddress,
        Symbol("participantIdentity") -> entity.participantIdentity,
        Symbol("participantName") -> entity.participantName,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into calendar_entry_sharers(
      calendar_id,
      participant_email_address,
      participant_identity,
      participant_name,
      tenant_id
    ) values (
      {calendarId},
      {participantEmailAddress},
      {participantIdentity},
      {participantName},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: CalendarEntrySharers)(implicit session: DBSession = autoSession): CalendarEntrySharers = {
    withSQL {
      update(CalendarEntrySharers).set(
        column.calendarEntrySharersId -> entity.calendarEntrySharersId,
        column.calendarId -> entity.calendarId,
        column.participantEmailAddress -> entity.participantEmailAddress,
        column.participantIdentity -> entity.participantIdentity,
        column.participantName -> entity.participantName,
        column.tenantId -> entity.tenantId
      ).where.eq(column.calendarEntrySharersId, entity.calendarEntrySharersId)
    }.update.apply()
    entity
  }

  def destroy(entity: CalendarEntrySharers)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CalendarEntrySharers).where.eq(column.calendarEntrySharersId, entity.calendarEntrySharersId) }.update.apply()
  }

}
