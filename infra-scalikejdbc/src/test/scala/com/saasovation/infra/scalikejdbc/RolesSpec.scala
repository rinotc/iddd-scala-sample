package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class RolesSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val r = Roles.syntax("r")

  behavior of "Roles"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Roles.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Roles.findBy(sqls.eq(r.roleId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Roles.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Roles.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Roles.findAllBy(sqls.eq(r.roleId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Roles.countBy(sqls.eq(r.roleId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Roles.create(roleId = "MyString", description = "MyString", groupId = "MyString", name = "MyString", supportsNesting = false, tenantId = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Roles.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Roles.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Roles.findAll().head
    val deleted = Roles.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Roles.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Roles.findAll()
    entities.foreach(e => Roles.destroy(e))
    val batchInserted = Roles.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
