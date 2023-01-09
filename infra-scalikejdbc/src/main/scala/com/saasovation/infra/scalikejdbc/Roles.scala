package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Roles(
  id: String,
  description: String,
  groupId: String,
  name: String,
  supportsNesting: Boolean,
  tenantId: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = Roles.autoSession): Roles = Roles.save(this)(session)

  def destroy()(implicit session: DBSession = Roles.autoSession): Int = Roles.destroy(this)(session)

}


object Roles extends SQLSyntaxSupport[Roles] {

  override val schemaName = Some("public")

  override val tableName = "roles"

  override val columns = Seq("id", "description", "group_id", "name", "supports_nesting", "tenant_id", "concurrency_version")

  def apply(r: SyntaxProvider[Roles])(rs: WrappedResultSet): Roles = apply(r.resultName)(rs)
  def apply(r: ResultName[Roles])(rs: WrappedResultSet): Roles = new Roles(
    id = rs.get(r.id),
    description = rs.get(r.description),
    groupId = rs.get(r.groupId),
    name = rs.get(r.name),
    supportsNesting = rs.get(r.supportsNesting),
    tenantId = rs.get(r.tenantId),
    concurrencyVersion = rs.get(r.concurrencyVersion)
  )

  val r = Roles.syntax("r")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[Roles] = {
    withSQL {
      select.from(Roles as r).where.eq(r.id, id)
    }.map(Roles(r.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Roles] = {
    withSQL(select.from(Roles as r)).map(Roles(r.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Roles as r)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Roles] = {
    withSQL {
      select.from(Roles as r).where.append(where)
    }.map(Roles(r.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Roles] = {
    withSQL {
      select.from(Roles as r).where.append(where)
    }.map(Roles(r.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Roles as r).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    description: String,
    groupId: String,
    name: String,
    supportsNesting: Boolean,
    tenantId: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Roles = {
    withSQL {
      insert.into(Roles).namedValues(
        column.id -> id,
        column.description -> description,
        column.groupId -> groupId,
        column.name -> name,
        column.supportsNesting -> supportsNesting,
        column.tenantId -> tenantId,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Roles(
      id = id,
      description = description,
      groupId = groupId,
      name = name,
      supportsNesting = supportsNesting,
      tenantId = tenantId,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Roles])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("description") -> entity.description,
        Symbol("groupId") -> entity.groupId,
        Symbol("name") -> entity.name,
        Symbol("supportsNesting") -> entity.supportsNesting,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into roles(
      id,
      description,
      group_id,
      name,
      supports_nesting,
      tenant_id,
      concurrency_version
    ) values (
      {id},
      {description},
      {groupId},
      {name},
      {supportsNesting},
      {tenantId},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Roles)(implicit session: DBSession = autoSession): Roles = {
    withSQL {
      update(Roles).set(
        column.id -> entity.id,
        column.description -> entity.description,
        column.groupId -> entity.groupId,
        column.name -> entity.name,
        column.supportsNesting -> entity.supportsNesting,
        column.tenantId -> entity.tenantId,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Roles)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Roles).where.eq(column.id, entity.id) }.update.apply()
  }

}
