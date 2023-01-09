package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class CalendarEntrySharersSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val ces = CalendarEntrySharers.syntax("ces")

  behavior of "CalendarEntrySharers"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = CalendarEntrySharers.find(123)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = CalendarEntrySharers.findBy(sqls.eq(ces.id, 123))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = CalendarEntrySharers.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = CalendarEntrySharers.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = CalendarEntrySharers.findAllBy(sqls.eq(ces.id, 123))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = CalendarEntrySharers.countBy(sqls.eq(ces.id, 123))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = CalendarEntrySharers.create(calendarId = "MyString", participantEmailAddress = "MyString", participantIdentity = "MyString", participantName = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = CalendarEntrySharers.findAll().head
    // TODO modify something
    val modified = entity
    val updated = CalendarEntrySharers.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = CalendarEntrySharers.findAll().head
    val deleted = CalendarEntrySharers.destroy(entity)
    deleted should be(1)
    val shouldBeNone = CalendarEntrySharers.find(123)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = CalendarEntrySharers.findAll()
    entities.foreach(e => CalendarEntrySharers.destroy(e))
    val batchInserted = CalendarEntrySharers.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
