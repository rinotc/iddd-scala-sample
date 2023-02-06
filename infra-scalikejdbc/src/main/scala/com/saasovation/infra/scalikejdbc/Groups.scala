package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Groups(
  groupId: String,
  description: String,
  name: String,
  tenantId: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = Groups.autoSession): Groups = Groups.save(this)(session)

  def destroy()(implicit session: DBSession = Groups.autoSession): Int = Groups.destroy(this)(session)

}


object Groups extends SQLSyntaxSupport[Groups] {

  override val schemaName = Some("public")

  override val tableName = "groups"

  override val columns = Seq("group_id", "description", "name", "tenant_id", "concurrency_version")

  def apply(g: SyntaxProvider[Groups])(rs: WrappedResultSet): Groups = apply(g.resultName)(rs)
  def apply(g: ResultName[Groups])(rs: WrappedResultSet): Groups = new Groups(
    groupId = rs.get(g.groupId),
    description = rs.get(g.description),
    name = rs.get(g.name),
    tenantId = rs.get(g.tenantId),
    concurrencyVersion = rs.get(g.concurrencyVersion)
  )

  val g = Groups.syntax("g")

  override val autoSession = AutoSession

  def find(groupId: String)(implicit session: DBSession = autoSession): Option[Groups] = {
    withSQL {
      select.from(Groups as g).where.eq(g.groupId, groupId)
    }.map(Groups(g.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Groups] = {
    withSQL(select.from(Groups as g)).map(Groups(g.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Groups as g)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Groups] = {
    withSQL {
      select.from(Groups as g).where.append(where)
    }.map(Groups(g.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Groups] = {
    withSQL {
      select.from(Groups as g).where.append(where)
    }.map(Groups(g.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Groups as g).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    groupId: String,
    description: String,
    name: String,
    tenantId: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Groups = {
    withSQL {
      insert.into(Groups).namedValues(
        column.groupId -> groupId,
        column.description -> description,
        column.name -> name,
        column.tenantId -> tenantId,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Groups(
      groupId = groupId,
      description = description,
      name = name,
      tenantId = tenantId,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Groups])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("groupId") -> entity.groupId,
        Symbol("description") -> entity.description,
        Symbol("name") -> entity.name,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into groups(
      group_id,
      description,
      name,
      tenant_id,
      concurrency_version
    ) values (
      {groupId},
      {description},
      {name},
      {tenantId},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Groups)(implicit session: DBSession = autoSession): Groups = {
    withSQL {
      update(Groups).set(
        column.groupId -> entity.groupId,
        column.description -> entity.description,
        column.name -> entity.name,
        column.tenantId -> entity.tenantId,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.groupId, entity.groupId)
    }.update.apply()
    entity
  }

  def destroy(entity: Groups)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Groups).where.eq(column.groupId, entity.groupId) }.update.apply()
  }

}
