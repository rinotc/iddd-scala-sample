package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class Discussions(
  discussionId: String,
  authorEmailAddress: String,
  authorIdentity: String,
  authorName: String,
  closed: Boolean,
  exclusiveOwner: Option[String] = None,
  forumId: String,
  subject: String,
  tenantId: String) {

  def save()(implicit session: DBSession = Discussions.autoSession): Discussions = Discussions.save(this)(session)

  def destroy()(implicit session: DBSession = Discussions.autoSession): Int = Discussions.destroy(this)(session)

}


object Discussions extends SQLSyntaxSupport[Discussions] {

  override val schemaName = Some("public")

  override val tableName = "discussions"

  override val columns = Seq("discussion_id", "author_email_address", "author_identity", "author_name", "closed", "exclusive_owner", "forum_id", "subject", "tenant_id")

  def apply(d: SyntaxProvider[Discussions])(rs: WrappedResultSet): Discussions = apply(d.resultName)(rs)
  def apply(d: ResultName[Discussions])(rs: WrappedResultSet): Discussions = new Discussions(
    discussionId = rs.get(d.discussionId),
    authorEmailAddress = rs.get(d.authorEmailAddress),
    authorIdentity = rs.get(d.authorIdentity),
    authorName = rs.get(d.authorName),
    closed = rs.get(d.closed),
    exclusiveOwner = rs.get(d.exclusiveOwner),
    forumId = rs.get(d.forumId),
    subject = rs.get(d.subject),
    tenantId = rs.get(d.tenantId)
  )

  val d = Discussions.syntax("d")

  override val autoSession = AutoSession

  def find(discussionId: String)(implicit session: DBSession = autoSession): Option[Discussions] = {
    withSQL {
      select.from(Discussions as d).where.eq(d.discussionId, discussionId)
    }.map(Discussions(d.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Discussions] = {
    withSQL(select.from(Discussions as d)).map(Discussions(d.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Discussions as d)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Discussions] = {
    withSQL {
      select.from(Discussions as d).where.append(where)
    }.map(Discussions(d.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Discussions] = {
    withSQL {
      select.from(Discussions as d).where.append(where)
    }.map(Discussions(d.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Discussions as d).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    discussionId: String,
    authorEmailAddress: String,
    authorIdentity: String,
    authorName: String,
    closed: Boolean,
    exclusiveOwner: Option[String] = None,
    forumId: String,
    subject: String,
    tenantId: String)(implicit session: DBSession = autoSession): Discussions = {
    withSQL {
      insert.into(Discussions).namedValues(
        column.discussionId -> discussionId,
        column.authorEmailAddress -> authorEmailAddress,
        column.authorIdentity -> authorIdentity,
        column.authorName -> authorName,
        column.closed -> closed,
        column.exclusiveOwner -> exclusiveOwner,
        column.forumId -> forumId,
        column.subject -> subject,
        column.tenantId -> tenantId
      )
    }.update.apply()

    Discussions(
      discussionId = discussionId,
      authorEmailAddress = authorEmailAddress,
      authorIdentity = authorIdentity,
      authorName = authorName,
      closed = closed,
      exclusiveOwner = exclusiveOwner,
      forumId = forumId,
      subject = subject,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[Discussions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("discussionId") -> entity.discussionId,
        Symbol("authorEmailAddress") -> entity.authorEmailAddress,
        Symbol("authorIdentity") -> entity.authorIdentity,
        Symbol("authorName") -> entity.authorName,
        Symbol("closed") -> entity.closed,
        Symbol("exclusiveOwner") -> entity.exclusiveOwner,
        Symbol("forumId") -> entity.forumId,
        Symbol("subject") -> entity.subject,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into discussions(
      discussion_id,
      author_email_address,
      author_identity,
      author_name,
      closed,
      exclusive_owner,
      forum_id,
      subject,
      tenant_id
    ) values (
      {discussionId},
      {authorEmailAddress},
      {authorIdentity},
      {authorName},
      {closed},
      {exclusiveOwner},
      {forumId},
      {subject},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Discussions)(implicit session: DBSession = autoSession): Discussions = {
    withSQL {
      update(Discussions).set(
        column.discussionId -> entity.discussionId,
        column.authorEmailAddress -> entity.authorEmailAddress,
        column.authorIdentity -> entity.authorIdentity,
        column.authorName -> entity.authorName,
        column.closed -> entity.closed,
        column.exclusiveOwner -> entity.exclusiveOwner,
        column.forumId -> entity.forumId,
        column.subject -> entity.subject,
        column.tenantId -> entity.tenantId
      ).where.eq(column.discussionId, entity.discussionId)
    }.update.apply()
    entity
  }

  def destroy(entity: Discussions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Discussions).where.eq(column.discussionId, entity.discussionId) }.update.apply()
  }

}
