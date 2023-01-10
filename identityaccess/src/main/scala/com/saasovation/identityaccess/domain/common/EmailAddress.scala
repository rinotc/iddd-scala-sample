package com.saasovation.identityaccess.domain.common

import com.saasovation.common.arch.base.ValueObject

final case class EmailAddress(value: String) extends ValueObject {

  import EmailAddress._

  invariant()

  private def invariant(): Unit = checkInvariant(value).left.foreach { message =>
    throw new IllegalArgumentException(s"check invariant failed. $message")
  }
}

object EmailAddress {

  private val MinLength: Int = 1

  private val MaxLength: Int = 100

  def validate(value: String): Either[String, EmailAddress] = checkInvariant(value).map(_ => apply(value))

  private def checkInvariant(value: String): Either[String, Unit] = for {
    _ <- mustBeNonEmpty(value)
    _ <- checkArgumentLength(value)
    _ <- checkRegex(value)
  } yield ()

  private def mustBeNonEmpty(value: String): Either[String, Unit] =
    Either.cond(value.nonEmpty, (), "the email address is required.")

  private def checkArgumentLength(value: String): Either[String, Unit] = Either.cond(
    value.lengthIs >= MinLength && value.lengthIs <= MaxLength,
    (),
    "email address must be 100 characters or less."
  )

  private def checkRegex(value: String): Either[String, Unit] = Either.cond(
    "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*".r.matches(value),
    (),
    "Email address form is invalid."
  )
}
