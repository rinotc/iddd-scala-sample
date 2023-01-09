package com.saasovation.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class RegistrationInvitations(
  id: String,
  description: String,
  startingOn: LocalDateTime,
  tenantId: String,
  until: Option[LocalDateTime] = None,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = RegistrationInvitations.autoSession): RegistrationInvitations = RegistrationInvitations.save(this)(session)

  def destroy()(implicit session: DBSession = RegistrationInvitations.autoSession): Int = RegistrationInvitations.destroy(this)(session)

}


object RegistrationInvitations extends SQLSyntaxSupport[RegistrationInvitations] {

  override val schemaName = Some("public")

  override val tableName = "registration_invitations"

  override val columns = Seq("id", "description", "starting_on", "tenant_id", "until", "concurrency_version")

  def apply(ri: SyntaxProvider[RegistrationInvitations])(rs: WrappedResultSet): RegistrationInvitations = apply(ri.resultName)(rs)
  def apply(ri: ResultName[RegistrationInvitations])(rs: WrappedResultSet): RegistrationInvitations = new RegistrationInvitations(
    id = rs.get(ri.id),
    description = rs.get(ri.description),
    startingOn = rs.get(ri.startingOn),
    tenantId = rs.get(ri.tenantId),
    until = rs.get(ri.until),
    concurrencyVersion = rs.get(ri.concurrencyVersion)
  )

  val ri = RegistrationInvitations.syntax("ri")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[RegistrationInvitations] = {
    withSQL {
      select.from(RegistrationInvitations as ri).where.eq(ri.id, id)
    }.map(RegistrationInvitations(ri.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[RegistrationInvitations] = {
    withSQL(select.from(RegistrationInvitations as ri)).map(RegistrationInvitations(ri.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(RegistrationInvitations as ri)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[RegistrationInvitations] = {
    withSQL {
      select.from(RegistrationInvitations as ri).where.append(where)
    }.map(RegistrationInvitations(ri.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[RegistrationInvitations] = {
    withSQL {
      select.from(RegistrationInvitations as ri).where.append(where)
    }.map(RegistrationInvitations(ri.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(RegistrationInvitations as ri).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    description: String,
    startingOn: LocalDateTime,
    tenantId: String,
    until: Option[LocalDateTime] = None,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): RegistrationInvitations = {
    withSQL {
      insert.into(RegistrationInvitations).namedValues(
        column.id -> id,
        column.description -> description,
        column.startingOn -> startingOn,
        column.tenantId -> tenantId,
        column.until -> until,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    RegistrationInvitations(
      id = id,
      description = description,
      startingOn = startingOn,
      tenantId = tenantId,
      until = until,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[RegistrationInvitations])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("description") -> entity.description,
        Symbol("startingOn") -> entity.startingOn,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("until") -> entity.until,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into registration_invitations(
      id,
      description,
      starting_on,
      tenant_id,
      until,
      concurrency_version
    ) values (
      {id},
      {description},
      {startingOn},
      {tenantId},
      {until},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: RegistrationInvitations)(implicit session: DBSession = autoSession): RegistrationInvitations = {
    withSQL {
      update(RegistrationInvitations).set(
        column.id -> entity.id,
        column.description -> entity.description,
        column.startingOn -> entity.startingOn,
        column.tenantId -> entity.tenantId,
        column.until -> entity.until,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: RegistrationInvitations)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(RegistrationInvitations).where.eq(column.id, entity.id) }.update.apply()
  }

}
