package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class TimeConstrainedProcessTrackerSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val tcpt = TimeConstrainedProcessTracker.syntax("tcpt")

  behavior of "TimeConstrainedProcessTracker"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = TimeConstrainedProcessTracker.find(1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = TimeConstrainedProcessTracker.findBy(sqls.eq(tcpt.timeConstrainedProcessTrackerId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = TimeConstrainedProcessTracker.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = TimeConstrainedProcessTracker.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = TimeConstrainedProcessTracker.findAllBy(sqls.eq(tcpt.timeConstrainedProcessTrackerId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = TimeConstrainedProcessTracker.countBy(sqls.eq(tcpt.timeConstrainedProcessTrackerId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = TimeConstrainedProcessTracker.create(allowedDuration = 1L, completed = false, description = "MyString", processId = "MyString", processInformedOfTimeout = false, processTimedOutEventType = "MyString", retryCount = 123, tenantId = "MyString", timeoutOccursOn = 1L, totalRetriesPermitted = 1L, concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = TimeConstrainedProcessTracker.findAll().head
    // TODO modify something
    val modified = entity
    val updated = TimeConstrainedProcessTracker.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = TimeConstrainedProcessTracker.findAll().head
    val deleted = TimeConstrainedProcessTracker.destroy(entity)
    deleted should be(1)
    val shouldBeNone = TimeConstrainedProcessTracker.find(1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = TimeConstrainedProcessTracker.findAll()
    entities.foreach(e => TimeConstrainedProcessTracker.destroy(e))
    val batchInserted = TimeConstrainedProcessTracker.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
