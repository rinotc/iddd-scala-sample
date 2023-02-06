package com.saasovation.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class Users(
  userId: String,
  enablementEnabled: Boolean,
  enablementEndDate: LocalDateTime,
  password: String,
  tenantId: String,
  username: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Int = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val schemaName = Some("public")

  override val tableName = "users"

  override val columns = Seq("user_id", "enablement_enabled", "enablement_end_date", "password", "tenant_id", "username", "concurrency_version")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    userId = rs.get(u.userId),
    enablementEnabled = rs.get(u.enablementEnabled),
    enablementEndDate = rs.get(u.enablementEndDate),
    password = rs.get(u.password),
    tenantId = rs.get(u.tenantId),
    username = rs.get(u.username),
    concurrencyVersion = rs.get(u.concurrencyVersion)
  )

  val u = Users.syntax("u")

  override val autoSession = AutoSession

  def find(userId: String)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.eq(u.userId, userId)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Users] = {
    withSQL(select.from(Users as u)).map(Users(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Users as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Users as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: String,
    enablementEnabled: Boolean,
    enablementEndDate: LocalDateTime,
    password: String,
    tenantId: String,
    username: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      insert.into(Users).namedValues(
        column.userId -> userId,
        column.enablementEnabled -> enablementEnabled,
        column.enablementEndDate -> enablementEndDate,
        column.password -> password,
        column.tenantId -> tenantId,
        column.username -> username,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.update.apply()

    Users(
      userId = userId,
      enablementEnabled = enablementEnabled,
      enablementEndDate = enablementEndDate,
      password = password,
      tenantId = tenantId,
      username = username,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[Users])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("userId") -> entity.userId,
        Symbol("enablementEnabled") -> entity.enablementEnabled,
        Symbol("enablementEndDate") -> entity.enablementEndDate,
        Symbol("password") -> entity.password,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("username") -> entity.username,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into users(
      user_id,
      enablement_enabled,
      enablement_end_date,
      password,
      tenant_id,
      username,
      concurrency_version
    ) values (
      {userId},
      {enablementEnabled},
      {enablementEndDate},
      {password},
      {tenantId},
      {username},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Users)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      update(Users).set(
        column.userId -> entity.userId,
        column.enablementEnabled -> entity.enablementEnabled,
        column.enablementEndDate -> entity.enablementEndDate,
        column.password -> entity.password,
        column.tenantId -> entity.tenantId,
        column.username -> entity.username,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: Users)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Users).where.eq(column.userId, entity.userId) }.update.apply()
  }

}
