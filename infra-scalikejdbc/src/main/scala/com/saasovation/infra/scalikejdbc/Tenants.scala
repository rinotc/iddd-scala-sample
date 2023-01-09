package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Tenants(
  id: String,
  active: Boolean,
  description: String,
  name: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = Tenants.autoSession): Tenants = Tenants.save(this)(session)

  def destroy()(implicit session: DBSession = Tenants.autoSession): Int = Tenants.destroy(this)(session)

}


object Tenants extends SQLSyntaxSupport[Tenants] {

  override val schemaName = Some("public")

  override val tableName = "tenants"

  override val columns = Seq("id", "active", "description", "name", "concurrency_version")

  def apply(t: SyntaxProvider[Tenants])(rs: WrappedResultSet): Tenants = apply(t.resultName)(rs)
  def apply(t: ResultName[Tenants])(rs: WrappedResultSet): Tenants = new Tenants(
    id = rs.get(t.id),
    active = rs.get(t.active),
    description = rs.get(t.description),
    name = rs.get(t.name),
    concurrencyVersion = rs.get(t.concurrencyVersion)
  )

  val t = Tenants.syntax("t")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[Tenants] = {
    withSQL {
      select.from(Tenants as t).where.eq(t.id, id)
    }.map(Tenants(t.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Tenants] = {
    withSQL(select.from(Tenants as t)).map(Tenants(t.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Tenants as t)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Tenants] = {
    withSQL {
      select.from(Tenants as t).where.append(where)
    }.map(Tenants(t.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Tenants] = {
    withSQL {
      select.from(Tenants as t).where.append(where)
    }.map(Tenants(t.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Tenants as t).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    active: Boolean,
    description: String,
    name: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Tenants = {
    withSQL {
      insert.into(Tenants).namedValues(
        column.id -> id,
        column.active -> active,
        column.description -> description,
        column.name -> name,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Tenants(
      id = id,
      active = active,
      description = description,
      name = name,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Tenants])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("active") -> entity.active,
        Symbol("description") -> entity.description,
        Symbol("name") -> entity.name,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into tenants(
      id,
      active,
      description,
      name,
      concurrency_version
    ) values (
      {id},
      {active},
      {description},
      {name},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Tenants)(implicit session: DBSession = autoSession): Tenants = {
    withSQL {
      update(Tenants).set(
        column.id -> entity.id,
        column.active -> entity.active,
        column.description -> entity.description,
        column.name -> entity.name,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Tenants)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Tenants).where.eq(column.id, entity.id) }.update.apply()
  }

}
