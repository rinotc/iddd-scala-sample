package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Forums(
  forumId: String,
  closed: Boolean,
  creatorEmailAddress: String,
  creatorIdentity: String,
  creatorName: String,
  description: String,
  exclusiveOwner: Option[String] = None,
  moderatorEmailAddress: String,
  moderatorIdentity: String,
  moderatorName: String,
  subject: String,
  tenantId: String) {

  def save()(implicit session: DBSession = Forums.autoSession): Forums = Forums.save(this)(session)

  def destroy()(implicit session: DBSession = Forums.autoSession): Int = Forums.destroy(this)(session)

}


object Forums extends SQLSyntaxSupport[Forums] {

  override val schemaName = Some("public")

  override val tableName = "forums"

  override val columns = Seq("forum_id", "closed", "creator_email_address", "creator_identity", "creator_name", "description", "exclusive_owner", "moderator_email_address", "moderator_identity", "moderator_name", "subject", "tenant_id")

  def apply(f: SyntaxProvider[Forums])(rs: WrappedResultSet): Forums = apply(f.resultName)(rs)
  def apply(f: ResultName[Forums])(rs: WrappedResultSet): Forums = new Forums(
    forumId = rs.get(f.forumId),
    closed = rs.get(f.closed),
    creatorEmailAddress = rs.get(f.creatorEmailAddress),
    creatorIdentity = rs.get(f.creatorIdentity),
    creatorName = rs.get(f.creatorName),
    description = rs.get(f.description),
    exclusiveOwner = rs.get(f.exclusiveOwner),
    moderatorEmailAddress = rs.get(f.moderatorEmailAddress),
    moderatorIdentity = rs.get(f.moderatorIdentity),
    moderatorName = rs.get(f.moderatorName),
    subject = rs.get(f.subject),
    tenantId = rs.get(f.tenantId)
  )

  val f = Forums.syntax("f")

  override val autoSession = AutoSession

  def find(forumId: String)(implicit session: DBSession = autoSession): Option[Forums] = {
    withSQL {
      select.from(Forums as f).where.eq(f.forumId, forumId)
    }.map(Forums(f.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Forums] = {
    withSQL(select.from(Forums as f)).map(Forums(f.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Forums as f)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Forums] = {
    withSQL {
      select.from(Forums as f).where.append(where)
    }.map(Forums(f.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Forums] = {
    withSQL {
      select.from(Forums as f).where.append(where)
    }.map(Forums(f.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Forums as f).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    forumId: String,
    closed: Boolean,
    creatorEmailAddress: String,
    creatorIdentity: String,
    creatorName: String,
    description: String,
    exclusiveOwner: Option[String] = None,
    moderatorEmailAddress: String,
    moderatorIdentity: String,
    moderatorName: String,
    subject: String,
    tenantId: String)(implicit session: DBSession = autoSession): Forums = {
    withSQL {
      insert.into(Forums).namedValues(
        column.forumId -> forumId,
        column.closed -> closed,
        column.creatorEmailAddress -> creatorEmailAddress,
        column.creatorIdentity -> creatorIdentity,
        column.creatorName -> creatorName,
        column.description -> description,
        column.exclusiveOwner -> exclusiveOwner,
        column.moderatorEmailAddress -> moderatorEmailAddress,
        column.moderatorIdentity -> moderatorIdentity,
        column.moderatorName -> moderatorName,
        column.subject -> subject,
        column.tenantId -> tenantId
      )
    }.update.apply()

    Forums(
      forumId = forumId,
      closed = closed,
      creatorEmailAddress = creatorEmailAddress,
      creatorIdentity = creatorIdentity,
      creatorName = creatorName,
      description = description,
      exclusiveOwner = exclusiveOwner,
      moderatorEmailAddress = moderatorEmailAddress,
      moderatorIdentity = moderatorIdentity,
      moderatorName = moderatorName,
      subject = subject,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[Forums])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("forumId") -> entity.forumId,
        Symbol("closed") -> entity.closed,
        Symbol("creatorEmailAddress") -> entity.creatorEmailAddress,
        Symbol("creatorIdentity") -> entity.creatorIdentity,
        Symbol("creatorName") -> entity.creatorName,
        Symbol("description") -> entity.description,
        Symbol("exclusiveOwner") -> entity.exclusiveOwner,
        Symbol("moderatorEmailAddress") -> entity.moderatorEmailAddress,
        Symbol("moderatorIdentity") -> entity.moderatorIdentity,
        Symbol("moderatorName") -> entity.moderatorName,
        Symbol("subject") -> entity.subject,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into forums(
      forum_id,
      closed,
      creator_email_address,
      creator_identity,
      creator_name,
      description,
      exclusive_owner,
      moderator_email_address,
      moderator_identity,
      moderator_name,
      subject,
      tenant_id
    ) values (
      {forumId},
      {closed},
      {creatorEmailAddress},
      {creatorIdentity},
      {creatorName},
      {description},
      {exclusiveOwner},
      {moderatorEmailAddress},
      {moderatorIdentity},
      {moderatorName},
      {subject},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Forums)(implicit session: DBSession = autoSession): Forums = {
    withSQL {
      update(Forums).set(
        column.forumId -> entity.forumId,
        column.closed -> entity.closed,
        column.creatorEmailAddress -> entity.creatorEmailAddress,
        column.creatorIdentity -> entity.creatorIdentity,
        column.creatorName -> entity.creatorName,
        column.description -> entity.description,
        column.exclusiveOwner -> entity.exclusiveOwner,
        column.moderatorEmailAddress -> entity.moderatorEmailAddress,
        column.moderatorIdentity -> entity.moderatorIdentity,
        column.moderatorName -> entity.moderatorName,
        column.subject -> entity.subject,
        column.tenantId -> entity.tenantId
      ).where.eq(column.forumId, entity.forumId)
    }.update.apply()
    entity
  }

  def destroy(entity: Forums)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Forums).where.eq(column.forumId, entity.forumId) }.update.apply()
  }

}
