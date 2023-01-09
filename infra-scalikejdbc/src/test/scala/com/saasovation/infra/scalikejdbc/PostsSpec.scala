package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._
import java.time.{LocalDateTime}


class PostsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val p = Posts.syntax("p")

  behavior of "Posts"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Posts.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Posts.findBy(sqls.eq(p.postId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Posts.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Posts.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Posts.findAllBy(sqls.eq(p.postId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Posts.countBy(sqls.eq(p.postId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Posts.create(postId = "MyString", authorEmailAddress = "MyString", authorIdentity = "MyString", authorName = "MyString", bodyText = "MyString", changedOn = null, createdOn = null, discussionId = "MyString", forumId = "MyString", replyToPostId = "MyString", subject = "MyString", tenantId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Posts.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Posts.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Posts.findAll().head
    val deleted = Posts.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Posts.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Posts.findAll()
    entities.foreach(e => Posts.destroy(e))
    val batchInserted = Posts.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
