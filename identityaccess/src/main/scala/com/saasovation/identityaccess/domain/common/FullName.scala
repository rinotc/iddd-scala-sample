package com.saasovation.identityaccess.domain.common

import com.saasovation.common.arch.base.ValueObject

final case class FullName(firstName: String, lastName: String) extends ValueObject {

  import FullName._

  invariant()

  val formattedName: String = s"$lastName $firstName"

  def withChangedFirstName(firstName: String): Either[String, FullName] = validate(firstName, this.lastName)

  def withChangedLastName(lastName: String): Either[String, FullName] = validate(this.firstName, lastName)

  private def invariant(): Unit = {
    checkInvariant(firstName, lastName).left.foreach { message =>
      throw new IllegalArgumentException(s"check invariant failed. $message")
    }
  }
}

object FullName {

  private val MinLength: Int = 1

  private val MaxLength: Int = 50

  private def mustBeNonEmpty(firstName: String, lastName: String): Either[String, Unit] = for {
    _ <- Either.cond(firstName.nonEmpty, (), "first name is required. must be non empty.")
    _ <- Either.cond(lastName.nonEmpty, (), "last name is required. must be non empty.")
  } yield ()

  private def checkArgumentLength(firstName: String, lastName: String): Either[String, Unit] = for {
    _ <- Either.cond(
      firstName.lengthIs >= MinLength && lastName.lengthIs <= MaxLength,
      (),
      "first name must be 50 characters or less."
    )
    _ <- Either.cond(
      lastName.lengthIs >= MinLength && lastName.lengthIs <= MaxLength,
      (),
      "last name must be 50 characters or less."
    )
  } yield ()

  private def checkInvariant(firstName: String, lastName: String): Either[String, Unit] = for {
    _ <- mustBeNonEmpty(firstName, lastName)
    _ <- checkArgumentLength(firstName, lastName)
  } yield ()

  def validate(firstName: String, lastName: String): Either[String, FullName] =
    checkInvariant(firstName, lastName).map(_ => apply(firstName, lastName))
}
