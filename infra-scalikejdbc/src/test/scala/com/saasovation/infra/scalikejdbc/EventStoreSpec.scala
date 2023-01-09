package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class EventStoreSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val es = EventStore.syntax("es")

  behavior of "EventStore"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = EventStore.find(1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = EventStore.findBy(sqls.eq(es.eventId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = EventStore.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = EventStore.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = EventStore.findAllBy(sqls.eq(es.eventId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = EventStore.countBy(sqls.eq(es.eventId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = EventStore.create(eventBody = null, eventType = "MyString", streamName = "MyString", streamVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = EventStore.findAll().head
    // TODO modify something
    val modified = entity
    val updated = EventStore.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = EventStore.findAll().head
    val deleted = EventStore.destroy(entity)
    deleted should be(1)
    val shouldBeNone = EventStore.find(1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = EventStore.findAll()
    entities.foreach(e => EventStore.destroy(e))
    val batchInserted = EventStore.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
