package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class TenantsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val t = Tenants.syntax("t")

  behavior of "Tenants"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Tenants.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Tenants.findBy(sqls.eq(t.tenantId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Tenants.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Tenants.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Tenants.findAllBy(sqls.eq(t.tenantId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Tenants.countBy(sqls.eq(t.tenantId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Tenants.create(tenantId = "MyString", active = false, description = "MyString", name = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Tenants.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Tenants.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Tenants.findAll().head
    val deleted = Tenants.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Tenants.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Tenants.findAll()
    entities.foreach(e => Tenants.destroy(e))
    val batchInserted = Tenants.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
