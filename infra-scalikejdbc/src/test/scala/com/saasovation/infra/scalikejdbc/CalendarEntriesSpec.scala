package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._
import java.time.{LocalDateTime}


class CalendarEntriesSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val ce = CalendarEntries.syntax("ce")

  behavior of "CalendarEntries"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = CalendarEntries.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = CalendarEntries.findBy(sqls.eq(ce.calendarEntryId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = CalendarEntries.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = CalendarEntries.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = CalendarEntries.findAllBy(sqls.eq(ce.calendarEntryId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = CalendarEntries.countBy(sqls.eq(ce.calendarEntryId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = CalendarEntries.create(calendarEntryId = "MyString", alarmAlarmUnits = 123, alarmAlarmUnitsType = "MyString", calendarId = "MyString", ownerEmailAddress = "MyString", ownerIdentity = "MyString", ownerName = "MyString", repetitionEnds = null, repetitionType = "MyString", tenantId = "MyString", timeSpanBegins = null, timeSpanEnds = null)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = CalendarEntries.findAll().head
    // TODO modify something
    val modified = entity
    val updated = CalendarEntries.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = CalendarEntries.findAll().head
    val deleted = CalendarEntries.destroy(entity)
    deleted should be(1)
    val shouldBeNone = CalendarEntries.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = CalendarEntries.findAll()
    entities.foreach(e => CalendarEntries.destroy(e))
    val batchInserted = CalendarEntries.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
