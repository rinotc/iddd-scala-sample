package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class CalendarEntryInviteesSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val cei = CalendarEntryInvitees.syntax("cei")

  behavior of "CalendarEntryInvitees"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = CalendarEntryInvitees.find(123)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = CalendarEntryInvitees.findBy(sqls.eq(cei.id, 123))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = CalendarEntryInvitees.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = CalendarEntryInvitees.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = CalendarEntryInvitees.findAllBy(sqls.eq(cei.id, 123))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = CalendarEntryInvitees.countBy(sqls.eq(cei.id, 123))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = CalendarEntryInvitees.create(calendarEntryId = "MyString", participantEmailAddress = "MyString", participantIdentity = "MyString", participantName = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = CalendarEntryInvitees.findAll().head
    // TODO modify something
    val modified = entity
    val updated = CalendarEntryInvitees.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = CalendarEntryInvitees.findAll().head
    val deleted = CalendarEntryInvitees.destroy(entity)
    deleted should be(1)
    val shouldBeNone = CalendarEntryInvitees.find(123)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = CalendarEntryInvitees.findAll()
    entities.foreach(e => CalendarEntryInvitees.destroy(e))
    val batchInserted = CalendarEntryInvitees.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
