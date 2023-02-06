package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class CalendarEntryInvitees(
  calendarEntryInviteesId: Int,
  calendarEntryId: String,
  participantEmailAddress: String,
  participantIdentity: String,
  participantName: String,
  tenantId: String) {

  def save()(implicit session: DBSession = CalendarEntryInvitees.autoSession): CalendarEntryInvitees = CalendarEntryInvitees.save(this)(session)

  def destroy()(implicit session: DBSession = CalendarEntryInvitees.autoSession): Int = CalendarEntryInvitees.destroy(this)(session)

}


object CalendarEntryInvitees extends SQLSyntaxSupport[CalendarEntryInvitees] {

  override val schemaName = Some("public")

  override val tableName = "calendar_entry_invitees"

  override val columns = Seq("calendar_entry_invitees_id", "calendar_entry_id", "participant_email_address", "participant_identity", "participant_name", "tenant_id")

  def apply(cei: SyntaxProvider[CalendarEntryInvitees])(rs: WrappedResultSet): CalendarEntryInvitees = apply(cei.resultName)(rs)
  def apply(cei: ResultName[CalendarEntryInvitees])(rs: WrappedResultSet): CalendarEntryInvitees = new CalendarEntryInvitees(
    calendarEntryInviteesId = rs.get(cei.calendarEntryInviteesId),
    calendarEntryId = rs.get(cei.calendarEntryId),
    participantEmailAddress = rs.get(cei.participantEmailAddress),
    participantIdentity = rs.get(cei.participantIdentity),
    participantName = rs.get(cei.participantName),
    tenantId = rs.get(cei.tenantId)
  )

  val cei = CalendarEntryInvitees.syntax("cei")

  override val autoSession = AutoSession

  def find(calendarEntryInviteesId: Int)(implicit session: DBSession = autoSession): Option[CalendarEntryInvitees] = {
    withSQL {
      select.from(CalendarEntryInvitees as cei).where.eq(cei.calendarEntryInviteesId, calendarEntryInviteesId)
    }.map(CalendarEntryInvitees(cei.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CalendarEntryInvitees] = {
    withSQL(select.from(CalendarEntryInvitees as cei)).map(CalendarEntryInvitees(cei.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CalendarEntryInvitees as cei)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CalendarEntryInvitees] = {
    withSQL {
      select.from(CalendarEntryInvitees as cei).where.append(where)
    }.map(CalendarEntryInvitees(cei.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CalendarEntryInvitees] = {
    withSQL {
      select.from(CalendarEntryInvitees as cei).where.append(where)
    }.map(CalendarEntryInvitees(cei.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CalendarEntryInvitees as cei).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    calendarEntryId: String,
    participantEmailAddress: String,
    participantIdentity: String,
    participantName: String,
    tenantId: String)(implicit session: DBSession = autoSession): CalendarEntryInvitees = {
    val generatedKey = withSQL {
      insert.into(CalendarEntryInvitees).namedValues(
        column.calendarEntryId -> calendarEntryId,
        column.participantEmailAddress -> participantEmailAddress,
        column.participantIdentity -> participantIdentity,
        column.participantName -> participantName,
        column.tenantId -> tenantId
      )
    }.updateAndReturnGeneratedKey.apply()

    CalendarEntryInvitees(
      calendarEntryInviteesId = generatedKey.toInt,
      calendarEntryId = calendarEntryId,
      participantEmailAddress = participantEmailAddress,
      participantIdentity = participantIdentity,
      participantName = participantName,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[CalendarEntryInvitees])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("calendarEntryId") -> entity.calendarEntryId,
        Symbol("participantEmailAddress") -> entity.participantEmailAddress,
        Symbol("participantIdentity") -> entity.participantIdentity,
        Symbol("participantName") -> entity.participantName,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into calendar_entry_invitees(
      calendar_entry_id,
      participant_email_address,
      participant_identity,
      participant_name,
      tenant_id
    ) values (
      {calendarEntryId},
      {participantEmailAddress},
      {participantIdentity},
      {participantName},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: CalendarEntryInvitees)(implicit session: DBSession = autoSession): CalendarEntryInvitees = {
    withSQL {
      update(CalendarEntryInvitees).set(
        column.calendarEntryInviteesId -> entity.calendarEntryInviteesId,
        column.calendarEntryId -> entity.calendarEntryId,
        column.participantEmailAddress -> entity.participantEmailAddress,
        column.participantIdentity -> entity.participantIdentity,
        column.participantName -> entity.participantName,
        column.tenantId -> entity.tenantId
      ).where.eq(column.calendarEntryInviteesId, entity.calendarEntryInviteesId)
    }.update.apply()
    entity
  }

  def destroy(entity: CalendarEntryInvitees)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CalendarEntryInvitees).where.eq(column.calendarEntryInviteesId, entity.calendarEntryInviteesId) }.update.apply()
  }

}
