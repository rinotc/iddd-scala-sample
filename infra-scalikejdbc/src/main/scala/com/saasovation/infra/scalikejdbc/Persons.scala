package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Persons(
  id: String,
  email: String,
  address: String,
  postalCode: Int,
  telephoneNumber: String,
  firstName: String,
  lastName: String,
  tenantId: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = Persons.autoSession): Persons = Persons.save(this)(session)

  def destroy()(implicit session: DBSession = Persons.autoSession): Int = Persons.destroy(this)(session)

}


object Persons extends SQLSyntaxSupport[Persons] {

  override val schemaName = Some("public")

  override val tableName = "persons"

  override val columns = Seq("id", "email", "address", "postal_code", "telephone_number", "first_name", "last_name", "tenant_id", "concurrency_version")

  def apply(p: SyntaxProvider[Persons])(rs: WrappedResultSet): Persons = apply(p.resultName)(rs)
  def apply(p: ResultName[Persons])(rs: WrappedResultSet): Persons = new Persons(
    id = rs.get(p.id),
    email = rs.get(p.email),
    address = rs.get(p.address),
    postalCode = rs.get(p.postalCode),
    telephoneNumber = rs.get(p.telephoneNumber),
    firstName = rs.get(p.firstName),
    lastName = rs.get(p.lastName),
    tenantId = rs.get(p.tenantId),
    concurrencyVersion = rs.get(p.concurrencyVersion)
  )

  val p = Persons.syntax("p")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[Persons] = {
    withSQL {
      select.from(Persons as p).where.eq(p.id, id)
    }.map(Persons(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Persons] = {
    withSQL(select.from(Persons as p)).map(Persons(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Persons as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Persons] = {
    withSQL {
      select.from(Persons as p).where.append(where)
    }.map(Persons(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Persons] = {
    withSQL {
      select.from(Persons as p).where.append(where)
    }.map(Persons(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Persons as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    email: String,
    address: String,
    postalCode: Int,
    telephoneNumber: String,
    firstName: String,
    lastName: String,
    tenantId: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Persons = {
    withSQL {
      insert.into(Persons).namedValues(
        column.id -> id,
        column.email -> email,
        column.address -> address,
        column.postalCode -> postalCode,
        column.telephoneNumber -> telephoneNumber,
        column.firstName -> firstName,
        column.lastName -> lastName,
        column.tenantId -> tenantId,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Persons(
      id = id,
      email = email,
      address = address,
      postalCode = postalCode,
      telephoneNumber = telephoneNumber,
      firstName = firstName,
      lastName = lastName,
      tenantId = tenantId,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Persons])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("email") -> entity.email,
        Symbol("address") -> entity.address,
        Symbol("postalCode") -> entity.postalCode,
        Symbol("telephoneNumber") -> entity.telephoneNumber,
        Symbol("firstName") -> entity.firstName,
        Symbol("lastName") -> entity.lastName,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into persons(
      id,
      email,
      address,
      postal_code,
      telephone_number,
      first_name,
      last_name,
      tenant_id,
      concurrency_version
    ) values (
      {id},
      {email},
      {address},
      {postalCode},
      {telephoneNumber},
      {firstName},
      {lastName},
      {tenantId},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Persons)(implicit session: DBSession = autoSession): Persons = {
    withSQL {
      update(Persons).set(
        column.id -> entity.id,
        column.email -> entity.email,
        column.address -> entity.address,
        column.postalCode -> entity.postalCode,
        column.telephoneNumber -> entity.telephoneNumber,
        column.firstName -> entity.firstName,
        column.lastName -> entity.lastName,
        column.tenantId -> entity.tenantId,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Persons)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Persons).where.eq(column.id, entity.id) }.update.apply()
  }

}
