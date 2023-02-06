package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class GroupMembersSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val gm = GroupMembers.syntax("gm")

  behavior of "GroupMembers"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = GroupMembers.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = GroupMembers.findBy(sqls.eq(gm.groupMemberId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = GroupMembers.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = GroupMembers.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = GroupMembers.findAllBy(sqls.eq(gm.groupMemberId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = GroupMembers.countBy(sqls.eq(gm.groupMemberId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = GroupMembers.create(groupMemberId = "MyString", name = "MyString", tenantId = "MyString", `type` = "MyString", groupId = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = GroupMembers.findAll().head
    // TODO modify something
    val modified = entity
    val updated = GroupMembers.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = GroupMembers.findAll().head
    val deleted = GroupMembers.destroy(entity)
    deleted should be(1)
    val shouldBeNone = GroupMembers.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = GroupMembers.findAll()
    entities.foreach(e => GroupMembers.destroy(e))
    val batchInserted = GroupMembers.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
