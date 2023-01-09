package com.saasovation.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class CalendarEntries(
  calendarEntryId: String,
  alarmAlarmUnits: Int,
  alarmAlarmUnitsType: String,
  calendarId: String,
  description: Option[String] = None,
  location: Option[String] = None,
  ownerEmailAddress: String,
  ownerIdentity: String,
  ownerName: String,
  repetitionEnds: LocalDateTime,
  repetitionType: String,
  tenantId: String,
  timeSpanBegins: LocalDateTime,
  timeSpanEnds: LocalDateTime) {

  def save()(implicit session: DBSession = CalendarEntries.autoSession): CalendarEntries = CalendarEntries.save(this)(session)

  def destroy()(implicit session: DBSession = CalendarEntries.autoSession): Int = CalendarEntries.destroy(this)(session)

}


object CalendarEntries extends SQLSyntaxSupport[CalendarEntries] {

  override val schemaName = Some("public")

  override val tableName = "calendar_entries"

  override val columns = Seq("calendar_entry_id", "alarm_alarm_units", "alarm_alarm_units_type", "calendar_id", "description", "location", "owner_email_address", "owner_identity", "owner_name", "repetition_ends", "repetition_type", "tenant_id", "time_span_begins", "time_span_ends")

  def apply(ce: SyntaxProvider[CalendarEntries])(rs: WrappedResultSet): CalendarEntries = apply(ce.resultName)(rs)
  def apply(ce: ResultName[CalendarEntries])(rs: WrappedResultSet): CalendarEntries = new CalendarEntries(
    calendarEntryId = rs.get(ce.calendarEntryId),
    alarmAlarmUnits = rs.get(ce.alarmAlarmUnits),
    alarmAlarmUnitsType = rs.get(ce.alarmAlarmUnitsType),
    calendarId = rs.get(ce.calendarId),
    description = rs.get(ce.description),
    location = rs.get(ce.location),
    ownerEmailAddress = rs.get(ce.ownerEmailAddress),
    ownerIdentity = rs.get(ce.ownerIdentity),
    ownerName = rs.get(ce.ownerName),
    repetitionEnds = rs.get(ce.repetitionEnds),
    repetitionType = rs.get(ce.repetitionType),
    tenantId = rs.get(ce.tenantId),
    timeSpanBegins = rs.get(ce.timeSpanBegins),
    timeSpanEnds = rs.get(ce.timeSpanEnds)
  )

  val ce = CalendarEntries.syntax("ce")

  override val autoSession = AutoSession

  def find(calendarEntryId: String)(implicit session: DBSession = autoSession): Option[CalendarEntries] = {
    withSQL {
      select.from(CalendarEntries as ce).where.eq(ce.calendarEntryId, calendarEntryId)
    }.map(CalendarEntries(ce.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CalendarEntries] = {
    withSQL(select.from(CalendarEntries as ce)).map(CalendarEntries(ce.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CalendarEntries as ce)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CalendarEntries] = {
    withSQL {
      select.from(CalendarEntries as ce).where.append(where)
    }.map(CalendarEntries(ce.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CalendarEntries] = {
    withSQL {
      select.from(CalendarEntries as ce).where.append(where)
    }.map(CalendarEntries(ce.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CalendarEntries as ce).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    calendarEntryId: String,
    alarmAlarmUnits: Int,
    alarmAlarmUnitsType: String,
    calendarId: String,
    description: Option[String] = None,
    location: Option[String] = None,
    ownerEmailAddress: String,
    ownerIdentity: String,
    ownerName: String,
    repetitionEnds: LocalDateTime,
    repetitionType: String,
    tenantId: String,
    timeSpanBegins: LocalDateTime,
    timeSpanEnds: LocalDateTime)(implicit session: DBSession = autoSession): CalendarEntries = {
    withSQL {
      insert.into(CalendarEntries).namedValues(
        column.calendarEntryId -> calendarEntryId,
        column.alarmAlarmUnits -> alarmAlarmUnits,
        column.alarmAlarmUnitsType -> alarmAlarmUnitsType,
        column.calendarId -> calendarId,
        column.description -> description,
        column.location -> location,
        column.ownerEmailAddress -> ownerEmailAddress,
        column.ownerIdentity -> ownerIdentity,
        column.ownerName -> ownerName,
        column.repetitionEnds -> repetitionEnds,
        column.repetitionType -> repetitionType,
        column.tenantId -> tenantId,
        column.timeSpanBegins -> timeSpanBegins,
        column.timeSpanEnds -> timeSpanEnds
      )
    }.update.apply()

    CalendarEntries(
      calendarEntryId = calendarEntryId,
      alarmAlarmUnits = alarmAlarmUnits,
      alarmAlarmUnitsType = alarmAlarmUnitsType,
      calendarId = calendarId,
      description = description,
      location = location,
      ownerEmailAddress = ownerEmailAddress,
      ownerIdentity = ownerIdentity,
      ownerName = ownerName,
      repetitionEnds = repetitionEnds,
      repetitionType = repetitionType,
      tenantId = tenantId,
      timeSpanBegins = timeSpanBegins,
      timeSpanEnds = timeSpanEnds)
  }

  def batchInsert(entities: collection.Seq[CalendarEntries])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("calendarEntryId") -> entity.calendarEntryId,
        Symbol("alarmAlarmUnits") -> entity.alarmAlarmUnits,
        Symbol("alarmAlarmUnitsType") -> entity.alarmAlarmUnitsType,
        Symbol("calendarId") -> entity.calendarId,
        Symbol("description") -> entity.description,
        Symbol("location") -> entity.location,
        Symbol("ownerEmailAddress") -> entity.ownerEmailAddress,
        Symbol("ownerIdentity") -> entity.ownerIdentity,
        Symbol("ownerName") -> entity.ownerName,
        Symbol("repetitionEnds") -> entity.repetitionEnds,
        Symbol("repetitionType") -> entity.repetitionType,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("timeSpanBegins") -> entity.timeSpanBegins,
        Symbol("timeSpanEnds") -> entity.timeSpanEnds))
    SQL("""insert into calendar_entries(
      calendar_entry_id,
      alarm_alarm_units,
      alarm_alarm_units_type,
      calendar_id,
      description,
      location,
      owner_email_address,
      owner_identity,
      owner_name,
      repetition_ends,
      repetition_type,
      tenant_id,
      time_span_begins,
      time_span_ends
    ) values (
      {calendarEntryId},
      {alarmAlarmUnits},
      {alarmAlarmUnitsType},
      {calendarId},
      {description},
      {location},
      {ownerEmailAddress},
      {ownerIdentity},
      {ownerName},
      {repetitionEnds},
      {repetitionType},
      {tenantId},
      {timeSpanBegins},
      {timeSpanEnds}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: CalendarEntries)(implicit session: DBSession = autoSession): CalendarEntries = {
    withSQL {
      update(CalendarEntries).set(
        column.calendarEntryId -> entity.calendarEntryId,
        column.alarmAlarmUnits -> entity.alarmAlarmUnits,
        column.alarmAlarmUnitsType -> entity.alarmAlarmUnitsType,
        column.calendarId -> entity.calendarId,
        column.description -> entity.description,
        column.location -> entity.location,
        column.ownerEmailAddress -> entity.ownerEmailAddress,
        column.ownerIdentity -> entity.ownerIdentity,
        column.ownerName -> entity.ownerName,
        column.repetitionEnds -> entity.repetitionEnds,
        column.repetitionType -> entity.repetitionType,
        column.tenantId -> entity.tenantId,
        column.timeSpanBegins -> entity.timeSpanBegins,
        column.timeSpanEnds -> entity.timeSpanEnds
      ).where.eq(column.calendarEntryId, entity.calendarEntryId)
    }.update.apply()
    entity
  }

  def destroy(entity: CalendarEntries)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CalendarEntries).where.eq(column.calendarEntryId, entity.calendarEntryId) }.update.apply()
  }

}
