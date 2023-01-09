package com.saasovation.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class PersonsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val p = Persons.syntax("p")

  behavior of "Persons"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Persons.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Persons.findBy(sqls.eq(p.id, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Persons.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Persons.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Persons.findAllBy(sqls.eq(p.id, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Persons.countBy(sqls.eq(p.id, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Persons.create(id = "MyString", email = "MyString", address = "MyString", postalCode = 123, telephoneNumber = "MyString", firstName = "MyString", lastName = "MyString", tenantId = "MyString", concurrencyVersion = 123)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Persons.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Persons.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Persons.findAll().head
    val deleted = Persons.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Persons.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Persons.findAll()
    entities.foreach(e => Persons.destroy(e))
    val batchInserted = Persons.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
