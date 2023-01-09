package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class CalendarsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val c = Calendars.syntax("c")

  behavior of "Calendars"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Calendars.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Calendars.findBy(sqls.eq(c.calendarId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Calendars.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Calendars.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Calendars.findAllBy(sqls.eq(c.calendarId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Calendars.countBy(sqls.eq(c.calendarId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Calendars.create(calendarId = "MyString", name = "MyString", ownerEmailAddress = "MyString", ownerIdentity = "MyString", ownerName = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Calendars.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Calendars.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Calendars.findAll().head
    val deleted = Calendars.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Calendars.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Calendars.findAll()
    entities.foreach(e => Calendars.destroy(e))
    val batchInserted = Calendars.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
