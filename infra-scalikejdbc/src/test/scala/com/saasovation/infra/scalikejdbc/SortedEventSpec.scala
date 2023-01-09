package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._
import java.time.{LocalDateTime}


class SortedEventSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val se = SortedEvent.syntax("se")

  behavior of "SortedEvent"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = SortedEvent.find(1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = SortedEvent.findBy(sqls.eq(se.eventId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = SortedEvent.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = SortedEvent.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = SortedEvent.findAllBy(sqls.eq(se.eventId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = SortedEvent.countBy(sqls.eq(se.eventId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = SortedEvent.create(eventBody = null, occurredOn = null, typeName = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = SortedEvent.findAll().head
    // TODO modify something
    val modified = entity
    val updated = SortedEvent.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = SortedEvent.findAll().head
    val deleted = SortedEvent.destroy(entity)
    deleted should be(1)
    val shouldBeNone = SortedEvent.find(1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = SortedEvent.findAll()
    entities.foreach(e => SortedEvent.destroy(e))
    val batchInserted = SortedEvent.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
