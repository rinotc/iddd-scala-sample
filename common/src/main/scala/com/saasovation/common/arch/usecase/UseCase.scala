package com.saasovation.common.arch.usecase

abstract class UseCase[TInput <: Input[TOutput], TOutput <: Output] {

  def handle(input: TInput): TOutput
}
