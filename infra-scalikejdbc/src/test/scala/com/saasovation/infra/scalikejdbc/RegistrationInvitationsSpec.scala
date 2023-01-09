package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._
import java.time.{LocalDateTime}


class RegistrationInvitationsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val ri = RegistrationInvitations.syntax("ri")

  behavior of "RegistrationInvitations"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = RegistrationInvitations.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = RegistrationInvitations.findBy(sqls.eq(ri.id, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = RegistrationInvitations.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = RegistrationInvitations.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = RegistrationInvitations.findAllBy(sqls.eq(ri.id, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = RegistrationInvitations.countBy(sqls.eq(ri.id, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = RegistrationInvitations.create(id = "MyString", description = "MyString", startingOn = null, tenantId = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = RegistrationInvitations.findAll().head
    // TODO modify something
    val modified = entity
    val updated = RegistrationInvitations.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = RegistrationInvitations.findAll().head
    val deleted = RegistrationInvitations.destroy(entity)
    deleted should be(1)
    val shouldBeNone = RegistrationInvitations.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = RegistrationInvitations.findAll()
    entities.foreach(e => RegistrationInvitations.destroy(e))
    val batchInserted = RegistrationInvitations.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
