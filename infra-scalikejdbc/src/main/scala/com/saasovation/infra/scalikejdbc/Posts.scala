package com.saasovation.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class Posts(
  postId: String,
  authorEmailAddress: String,
  authorIdentity: String,
  authorName: String,
  bodyText: String,
  changedOn: LocalDateTime,
  createdOn: LocalDateTime,
  discussionId: String,
  forumId: String,
  replyToPostId: String,
  subject: String,
  tenantId: String) {

  def save()(implicit session: DBSession = Posts.autoSession): Posts = Posts.save(this)(session)

  def destroy()(implicit session: DBSession = Posts.autoSession): Int = Posts.destroy(this)(session)

}


object Posts extends SQLSyntaxSupport[Posts] {

  override val schemaName = Some("public")

  override val tableName = "posts"

  override val columns = Seq("post_id", "author_email_address", "author_identity", "author_name", "body_text", "changed_on", "created_on", "discussion_id", "forum_id", "reply_to_post_id", "subject", "tenant_id")

  def apply(p: SyntaxProvider[Posts])(rs: WrappedResultSet): Posts = apply(p.resultName)(rs)
  def apply(p: ResultName[Posts])(rs: WrappedResultSet): Posts = new Posts(
    postId = rs.get(p.postId),
    authorEmailAddress = rs.get(p.authorEmailAddress),
    authorIdentity = rs.get(p.authorIdentity),
    authorName = rs.get(p.authorName),
    bodyText = rs.get(p.bodyText),
    changedOn = rs.get(p.changedOn),
    createdOn = rs.get(p.createdOn),
    discussionId = rs.get(p.discussionId),
    forumId = rs.get(p.forumId),
    replyToPostId = rs.get(p.replyToPostId),
    subject = rs.get(p.subject),
    tenantId = rs.get(p.tenantId)
  )

  val p = Posts.syntax("p")

  override val autoSession = AutoSession

  def find(postId: String)(implicit session: DBSession = autoSession): Option[Posts] = {
    withSQL {
      select.from(Posts as p).where.eq(p.postId, postId)
    }.map(Posts(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Posts] = {
    withSQL(select.from(Posts as p)).map(Posts(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Posts as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Posts] = {
    withSQL {
      select.from(Posts as p).where.append(where)
    }.map(Posts(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Posts] = {
    withSQL {
      select.from(Posts as p).where.append(where)
    }.map(Posts(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Posts as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    postId: String,
    authorEmailAddress: String,
    authorIdentity: String,
    authorName: String,
    bodyText: String,
    changedOn: LocalDateTime,
    createdOn: LocalDateTime,
    discussionId: String,
    forumId: String,
    replyToPostId: String,
    subject: String,
    tenantId: String)(implicit session: DBSession = autoSession): Posts = {
    withSQL {
      insert.into(Posts).namedValues(
        column.postId -> postId,
        column.authorEmailAddress -> authorEmailAddress,
        column.authorIdentity -> authorIdentity,
        column.authorName -> authorName,
        column.bodyText -> bodyText,
        column.changedOn -> changedOn,
        column.createdOn -> createdOn,
        column.discussionId -> discussionId,
        column.forumId -> forumId,
        column.replyToPostId -> replyToPostId,
        column.subject -> subject,
        column.tenantId -> tenantId
      )
    }.update.apply()

    Posts(
      postId = postId,
      authorEmailAddress = authorEmailAddress,
      authorIdentity = authorIdentity,
      authorName = authorName,
      bodyText = bodyText,
      changedOn = changedOn,
      createdOn = createdOn,
      discussionId = discussionId,
      forumId = forumId,
      replyToPostId = replyToPostId,
      subject = subject,
      tenantId = tenantId)
  }

  def batchInsert(entities: collection.Seq[Posts])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("postId") -> entity.postId,
        Symbol("authorEmailAddress") -> entity.authorEmailAddress,
        Symbol("authorIdentity") -> entity.authorIdentity,
        Symbol("authorName") -> entity.authorName,
        Symbol("bodyText") -> entity.bodyText,
        Symbol("changedOn") -> entity.changedOn,
        Symbol("createdOn") -> entity.createdOn,
        Symbol("discussionId") -> entity.discussionId,
        Symbol("forumId") -> entity.forumId,
        Symbol("replyToPostId") -> entity.replyToPostId,
        Symbol("subject") -> entity.subject,
        Symbol("tenantId") -> entity.tenantId))
    SQL("""insert into posts(
      post_id,
      author_email_address,
      author_identity,
      author_name,
      body_text,
      changed_on,
      created_on,
      discussion_id,
      forum_id,
      reply_to_post_id,
      subject,
      tenant_id
    ) values (
      {postId},
      {authorEmailAddress},
      {authorIdentity},
      {authorName},
      {bodyText},
      {changedOn},
      {createdOn},
      {discussionId},
      {forumId},
      {replyToPostId},
      {subject},
      {tenantId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Posts)(implicit session: DBSession = autoSession): Posts = {
    withSQL {
      update(Posts).set(
        column.postId -> entity.postId,
        column.authorEmailAddress -> entity.authorEmailAddress,
        column.authorIdentity -> entity.authorIdentity,
        column.authorName -> entity.authorName,
        column.bodyText -> entity.bodyText,
        column.changedOn -> entity.changedOn,
        column.createdOn -> entity.createdOn,
        column.discussionId -> entity.discussionId,
        column.forumId -> entity.forumId,
        column.replyToPostId -> entity.replyToPostId,
        column.subject -> entity.subject,
        column.tenantId -> entity.tenantId
      ).where.eq(column.postId, entity.postId)
    }.update.apply()
    entity
  }

  def destroy(entity: Posts)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Posts).where.eq(column.postId, entity.postId) }.update.apply()
  }

}
