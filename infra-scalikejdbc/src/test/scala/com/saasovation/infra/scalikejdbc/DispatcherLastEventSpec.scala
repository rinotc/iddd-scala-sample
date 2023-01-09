package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class DispatcherLastEventSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val dle = DispatcherLastEvent.syntax("dle")

  behavior of "DispatcherLastEvent"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = DispatcherLastEvent.find(1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = DispatcherLastEvent.findBy(sqls.eq(dle.eventId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = DispatcherLastEvent.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = DispatcherLastEvent.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = DispatcherLastEvent.findAllBy(sqls.eq(dle.eventId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = DispatcherLastEvent.countBy(sqls.eq(dle.eventId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = DispatcherLastEvent.create(eventId = 1L)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = DispatcherLastEvent.findAll().head
    // TODO modify something
    val modified = entity
    val updated = DispatcherLastEvent.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = DispatcherLastEvent.findAll().head
    val deleted = DispatcherLastEvent.destroy(entity)
    deleted should be(1)
    val shouldBeNone = DispatcherLastEvent.find(1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = DispatcherLastEvent.findAll()
    entities.foreach(e => DispatcherLastEvent.destroy(e))
    val batchInserted = DispatcherLastEvent.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
