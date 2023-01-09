package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class DiscussionsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val d = Discussions.syntax("d")

  behavior of "Discussions"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Discussions.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Discussions.findBy(sqls.eq(d.discussionId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Discussions.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Discussions.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Discussions.findAllBy(sqls.eq(d.discussionId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Discussions.countBy(sqls.eq(d.discussionId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Discussions.create(discussionId = "MyString", authorEmailAddress = "MyString", authorIdentity = "MyString", authorName = "MyString", closed = false, forumId = "MyString", subject = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Discussions.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Discussions.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Discussions.findAll().head
    val deleted = Discussions.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Discussions.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Discussions.findAll()
    entities.foreach(e => Discussions.destroy(e))
    val batchInserted = Discussions.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
