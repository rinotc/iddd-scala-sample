package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class PublishedNotificationTrackerSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val pnt = PublishedNotificationTracker.syntax("pnt")

  behavior of "PublishedNotificationTracker"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = PublishedNotificationTracker.find(1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = PublishedNotificationTracker.findBy(sqls.eq(pnt.publishedNotificationTrackerId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = PublishedNotificationTracker.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = PublishedNotificationTracker.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = PublishedNotificationTracker.findAllBy(sqls.eq(pnt.publishedNotificationTrackerId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = PublishedNotificationTracker.countBy(sqls.eq(pnt.publishedNotificationTrackerId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = PublishedNotificationTracker.create(mostRecentPublishedNotificationId = 1L, typeName = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = PublishedNotificationTracker.findAll().head
    // TODO modify something
    val modified = entity
    val updated = PublishedNotificationTracker.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = PublishedNotificationTracker.findAll().head
    val deleted = PublishedNotificationTracker.destroy(entity)
    deleted should be(1)
    val shouldBeNone = PublishedNotificationTracker.find(1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = PublishedNotificationTracker.findAll()
    entities.foreach(e => PublishedNotificationTracker.destroy(e))
    val batchInserted = PublishedNotificationTracker.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
