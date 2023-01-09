package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class GroupsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val g = Groups.syntax("g")

  behavior of "Groups"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Groups.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Groups.findBy(sqls.eq(g.id, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Groups.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Groups.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Groups.findAllBy(sqls.eq(g.id, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Groups.countBy(sqls.eq(g.id, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Groups.create(id = "MyString", description = "MyString", name = "MyString", tenantId = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Groups.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Groups.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Groups.findAll().head
    val deleted = Groups.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Groups.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Groups.findAll()
    entities.foreach(e => Groups.destroy(e))
    val batchInserted = Groups.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
