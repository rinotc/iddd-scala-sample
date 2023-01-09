package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Calendars(
  calendarId: String,
  description: Option[String] = None,
  name: String,
  ownerEmailAddress: String,
  ownerIdentity: String,
  ownerName: String,
  tenantId: String) {

  def save()(implicit session: DBSession = Calendars.autoSession): Calendars = Calendars.save(this)(session)

  def destroy()(implicit session: DBSession = Calendars.autoSession): Int = Calendars.destroy(this)(session)

}


object Calendars extends SQLSyntaxSupport[Calendars] {

  override val schemaName = Some("public")

  override val tableName = "calendars"

  override val columns = Seq("calendar_id", "description", "name", "owner_email_address", "owner_identity", "owner_name", "tenant_id")

  def apply(c: SyntaxProvider[Calendars])(rs: WrappedResultSet): Calendars = apply(c.resultName)(rs)
  def apply(c: ResultName[Calendars])(rs: WrappedResultSet): Calendars = new Calendars(
    calendarId = rs.get(c.calendarId),
    description = rs.get(c.description),
    name = rs.get(c.name),
    ownerEmailAddress = rs.get(c.ownerEmailAddress),
    ownerIdentity = rs.get(c.ownerIdentity),
    ownerName = rs.get(c.ownerName),
    tenantId = rs.get(c.tenantId)
  )

  val c = Calendars.syntax("c")

  override val autoSession = AutoSession

  def find(calendarId: String)(implicit session: DBSession = autoSession): Option[Calendars] = {
    withSQL {
      select.from(Calendars as c).where.eq(c.calendarId, calendarId)
    }.map(Calendars(c.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Calendars] = {
    withSQL(select.from(Calendars as c)).map(Calendars(c.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Calendars as c)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Calendars] = {
    withSQL {
      select.from(Calendars as c).where.append(where)
    }.map(Calendars(c.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Calendars] = {
    withSQL {
      select.from(Calendars as c).where.append(where)
    }.map(Calendars(c.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Calendars as c).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    calendarId: String,
    description: Option[String] = None,
    name: String,
    ownerEmailAddress: String,
    ownerIdentity: String,
    ownerName: String,
    tenantId: String)(implicit session: DBSession = autoSession): Calendars = {
    withSQL {
      insert.into(Calendars).namedValues(
        column.calendarId -> calendarId,
        column.description -> description,
        column.name -> name,
        column.ownerEmailAddress -> ownerEmailAddress,
        column.ownerIdentity -> ownerIdentity,
        column.ownerName -> ownerName,
        column.tenantId -> tenantId
      )
    }.update.apply()

    Calendars(
      calendarId = calendarId,
      description = description,
      name = name,
      ownerEmailAddress = ownerEmailAddress,
      ownerIdentity = ownerIdentity,
      ownerName = ownerName,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[Calendars])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("calendarId") -> entity.calendarId,
        Symbol("description") -> entity.description,
        Symbol("name") -> entity.name,
        Symbol("ownerEmailAddress") -> entity.ownerEmailAddress,
        Symbol("ownerIdentity") -> entity.ownerIdentity,
        Symbol("ownerName") -> entity.ownerName,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into calendars(
      calendar_id,
      description,
      name,
      owner_email_address,
      owner_identity,
      owner_name,
      tenant_id
    ) values (
      {calendarId},
      {description},
      {name},
      {ownerEmailAddress},
      {ownerIdentity},
      {ownerName},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Calendars)(implicit session: DBSession = autoSession): Calendars = {
    withSQL {
      update(Calendars).set(
        column.calendarId -> entity.calendarId,
        column.description -> entity.description,
        column.name -> entity.name,
        column.ownerEmailAddress -> entity.ownerEmailAddress,
        column.ownerIdentity -> entity.ownerIdentity,
        column.ownerName -> entity.ownerName,
        column.tenantId -> entity.tenantId
      ).where.eq(column.calendarId, entity.calendarId)
    }.update.apply()
    entity
  }

  def destroy(entity: Calendars)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Calendars).where.eq(column.calendarId, entity.calendarId) }.update.apply()
  }

}
