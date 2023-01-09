package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class PublishedNotificationTracker(
  publishedNotificationTrackerId: Long,
  mostRecentPublishedNotificationId: Long,
  typeName: String,
  concurrencyVersion: Int) {

  def save()(implicit session: DBSession = PublishedNotificationTracker.autoSession): PublishedNotificationTracker = PublishedNotificationTracker.save(this)(session)

  def destroy()(implicit session: DBSession = PublishedNotificationTracker.autoSession): Int = PublishedNotificationTracker.destroy(this)(session)

}


object PublishedNotificationTracker extends SQLSyntaxSupport[PublishedNotificationTracker] {

  override val schemaName = Some("public")

  override val tableName = "published_notification_tracker"

  override val columns = Seq("published_notification_tracker_id", "most_recent_published_notification_id", "type_name", "concurrency_version")

  def apply(pnt: SyntaxProvider[PublishedNotificationTracker])(rs: WrappedResultSet): PublishedNotificationTracker = apply(pnt.resultName)(rs)
  def apply(pnt: ResultName[PublishedNotificationTracker])(rs: WrappedResultSet): PublishedNotificationTracker = new PublishedNotificationTracker(
    publishedNotificationTrackerId = rs.get(pnt.publishedNotificationTrackerId),
    mostRecentPublishedNotificationId = rs.get(pnt.mostRecentPublishedNotificationId),
    typeName = rs.get(pnt.typeName),
    concurrencyVersion = rs.get(pnt.concurrencyVersion)
  )

  val pnt = PublishedNotificationTracker.syntax("pnt")

  override val autoSession = AutoSession

  def find(publishedNotificationTrackerId: Long)(implicit session: DBSession = autoSession): Option[PublishedNotificationTracker] = {
    withSQL {
      select.from(PublishedNotificationTracker as pnt).where.eq(pnt.publishedNotificationTrackerId, publishedNotificationTrackerId)
    }.map(PublishedNotificationTracker(pnt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PublishedNotificationTracker] = {
    withSQL(select.from(PublishedNotificationTracker as pnt)).map(PublishedNotificationTracker(pnt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PublishedNotificationTracker as pnt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PublishedNotificationTracker] = {
    withSQL {
      select.from(PublishedNotificationTracker as pnt).where.append(where)
    }.map(PublishedNotificationTracker(pnt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PublishedNotificationTracker] = {
    withSQL {
      select.from(PublishedNotificationTracker as pnt).where.append(where)
    }.map(PublishedNotificationTracker(pnt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PublishedNotificationTracker as pnt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mostRecentPublishedNotificationId: Long,
    typeName: String,
    concurrencyVersion: Int)(implicit session: DBSession = autoSession): PublishedNotificationTracker = {
    val generatedKey = withSQL {
      insert.into(PublishedNotificationTracker).namedValues(
        column.mostRecentPublishedNotificationId -> mostRecentPublishedNotificationId,
        column.typeName -> typeName,
        column.concurrencyVersion -> concurrencyVersion
      )
    }.updateAndReturnGeneratedKey.apply()

    PublishedNotificationTracker(
      publishedNotificationTrackerId = generatedKey,
      mostRecentPublishedNotificationId = mostRecentPublishedNotificationId,
      typeName = typeName,
      concurrencyVersion = concurrencyVersion)
  }

  def batchInsert(entities: collection.Seq[PublishedNotificationTracker])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("mostRecentPublishedNotificationId") -> entity.mostRecentPublishedNotificationId,
        Symbol("typeName") -> entity.typeName,
        Symbol("concurrencyVersion") -> entity.concurrencyVersion))
    SQL("""insert into published_notification_tracker(
      most_recent_published_notification_id,
      type_name,
      concurrency_version
    ) values (
      {mostRecentPublishedNotificationId},
      {typeName},
      {concurrencyVersion}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: PublishedNotificationTracker)(implicit session: DBSession = autoSession): PublishedNotificationTracker = {
    withSQL {
      update(PublishedNotificationTracker).set(
        column.publishedNotificationTrackerId -> entity.publishedNotificationTrackerId,
        column.mostRecentPublishedNotificationId -> entity.mostRecentPublishedNotificationId,
        column.typeName -> entity.typeName,
        column.concurrencyVersion -> entity.concurrencyVersion
      ).where.eq(column.publishedNotificationTrackerId, entity.publishedNotificationTrackerId)
    }.update.apply()
    entity
  }

  def destroy(entity: PublishedNotificationTracker)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PublishedNotificationTracker).where.eq(column.publishedNotificationTrackerId, entity.publishedNotificationTrackerId) }.update.apply()
  }

}
