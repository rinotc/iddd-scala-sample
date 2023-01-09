package com.saasovation.infra.scalikejdbc

import scalikejdbc._

case class GroupMembers(
  id: String,
  name: String,
  tenantId: String,
  `type`: String,
  groupId: String) {

  def save()(implicit session: DBSession = GroupMembers.autoSession): GroupMembers = GroupMembers.save(this)(session)

  def destroy()(implicit session: DBSession = GroupMembers.autoSession): Int = GroupMembers.destroy(this)(session)

}


object GroupMembers extends SQLSyntaxSupport[GroupMembers] {

  override val schemaName = Some("public")

  override val tableName = "group_members"

  override val columns = Seq("id", "name", "tenant_id", "type", "group_id")

  def apply(gm: SyntaxProvider[GroupMembers])(rs: WrappedResultSet): GroupMembers = apply(gm.resultName)(rs)
  def apply(gm: ResultName[GroupMembers])(rs: WrappedResultSet): GroupMembers = new GroupMembers(
    id = rs.get(gm.id),
    name = rs.get(gm.name),
    tenantId = rs.get(gm.tenantId),
    `type` = rs.get(gm.`type`),
    groupId = rs.get(gm.groupId)
  )

  val gm = GroupMembers.syntax("gm")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[GroupMembers] = {
    withSQL {
      select.from(GroupMembers as gm).where.eq(gm.id, id)
    }.map(GroupMembers(gm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GroupMembers] = {
    withSQL(select.from(GroupMembers as gm)).map(GroupMembers(gm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GroupMembers as gm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GroupMembers] = {
    withSQL {
      select.from(GroupMembers as gm).where.append(where)
    }.map(GroupMembers(gm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GroupMembers] = {
    withSQL {
      select.from(GroupMembers as gm).where.append(where)
    }.map(GroupMembers(gm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GroupMembers as gm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    name: String,
    tenantId: String,
    `type`: String,
    groupId: String)(implicit session: DBSession = autoSession): GroupMembers = {
    withSQL {
      insert.into(GroupMembers).namedValues(
        column.id -> id,
        column.name -> name,
        column.tenantId -> tenantId,
        column.`type` -> `type`,
        column.groupId -> groupId
      )
    }.update.apply()

    GroupMembers(
      id = id,
      name = name,
      tenantId = tenantId,
      `type` = `type`,
      groupId = groupId)
  }

  def batchInsert(entities: collection.Seq[GroupMembers])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("id") -> entity.id,
        Symbol("name") -> entity.name,
        Symbol("tenantId") -> entity.tenantId,
        Symbol("type") -> entity.`type`,
        Symbol("groupId") -> entity.groupId))
    SQL("""insert into group_members(
      id,
      name,
      tenant_id,
      type,
      group_id
    ) values (
      {id},
      {name},
      {tenantId},
      {type},
      {groupId}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: GroupMembers)(implicit session: DBSession = autoSession): GroupMembers = {
    withSQL {
      update(GroupMembers).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.tenantId -> entity.tenantId,
        column.`type` -> entity.`type`,
        column.groupId -> entity.groupId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: GroupMembers)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GroupMembers).where.eq(column.id, entity.id) }.update.apply()
  }

}
