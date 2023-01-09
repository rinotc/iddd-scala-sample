package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class ForumsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val f = Forums.syntax("f")

  behavior of "Forums"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Forums.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Forums.findBy(sqls.eq(f.forumId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Forums.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Forums.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Forums.findAllBy(sqls.eq(f.forumId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Forums.countBy(sqls.eq(f.forumId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Forums.create(forumId = "MyString", closed = false, creatorEmailAddress = "MyString", creatorIdentity = "MyString", creatorName = "MyString", description = "MyString", moderatorEmailAddress = "MyString", moderatorIdentity = "MyString", moderatorName = "MyString", subject = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Forums.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Forums.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Forums.findAll().head
    val deleted = Forums.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Forums.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Forums.findAll()
    entities.foreach(e => Forums.destroy(e))
    val batchInserted = Forums.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
