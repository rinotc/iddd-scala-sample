package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Groups(
  id: String,
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

  override val columns = Seq("id", "description", "name", "tenant_id", "concurrency_version")

  def apply(g: SyntaxProvider[Groups])(rs: WrappedResultSet): Groups = apply(g.resultName)(rs)
  def apply(g: ResultName[Groups])(rs: WrappedResultSet): Groups = new Groups(
    id = rs.get(g.id),
    description = rs.get(g.description),
    name = rs.get(g.name),
    tenantId = rs.get(g.tenantId),
    concurrencyVersion = rs.get(g.concurrencyVersion)
  )

  val g = Groups.syntax("g")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[Groups] = {
    withSQL {
      select.from(Groups as g).where.eq(g.id, id)
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
    id: String,
    description: String,
    name: String,
    tenantId: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Groups = {
    withSQL {
      insert.into(Groups).namedValues(
        column.id -> id,
        column.description -> description,
        column.name -> name,
        column.tenantId -> tenantId,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Groups(
      id = id,
      description = description,
      name = name,
      tenantId = tenantId,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Groups])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("description") -> entity.description,
        Symbol("name") -> entity.name,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into groups(
      id,
      description,
      name,
      tenant_id,
      concurrency_version
    ) values (
      {id},
      {description},
      {name},
      {tenantId},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Groups)(implicit session: DBSession = autoSession): Groups = {
    withSQL {
      update(Groups).set(
        column.id -> entity.id,
        column.description -> entity.description,
        column.name -> entity.name,
        column.tenantId -> entity.tenantId,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Groups)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Groups).where.eq(column.id, entity.id) }.update.apply()
  }

}
